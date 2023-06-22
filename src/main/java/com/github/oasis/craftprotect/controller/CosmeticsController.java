package com.github.oasis.craftprotect.controller;

import com.github.oasis.craftprotect.CraftProtectPlugin;
import com.github.oasis.craftprotect.model.cosmetic.AttachedTo;
import com.github.oasis.craftprotect.model.cosmetic.CosmeticModel;
import com.github.oasis.craftprotect.model.cosmetic.CosmeticTransformation;
import com.github.oasis.craftprotect.utils.VectorSerializer;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import de.javagl.obj.Obj;
import de.javagl.obj.ObjReader;
import de.javagl.obj.ObjUtils;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.*;
import java.util.*;

@Singleton
@Data
public class CosmeticsController {

    private CraftProtectPlugin plugin;
    private final Map<String, CosmeticModel> modelMap = new HashMap<>();

    @Inject
    public CosmeticsController(CraftProtectPlugin plugin) {
        this.plugin = plugin;
        reloadModels();
    }

    public void reloadModels() {
        this.modelMap.clear();
        File cosmeticsFolder = new File(plugin.getDataFolder(), "cosmetics");
        cosmeticsFolder.mkdirs();
        File[] files = cosmeticsFolder.listFiles((dir, name) -> {
            System.out.println(name);
            return name.endsWith(".obj");
        });
        if (files == null) return;
        Gson gson = new GsonBuilder()
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .registerTypeHierarchyAdapter(Vector.class, new VectorSerializer()).create();
        for (File file : files) {
            CosmeticModel model;

            String id = file.getName().substring(0, file.getName().lastIndexOf('.'));

            File config = new File(cosmeticsFolder, id + ".json");
            if (config.isFile()) {
                try (FileReader reader = new FileReader(config)) {
                    model = gson.fromJson(reader, CosmeticModel.class);
                } catch (Exception e) {
                    System.err.println("Failed to read config for " + file.getName() + " cosmetic");
                    e.printStackTrace();
                    continue;
                }
            } else {
                model = new CosmeticModel();
            }
            try (FileInputStream input = new FileInputStream(file)) {
                model.setList(loadModel(input, model.getTransformation()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            modelMap.put(id, model);
        }
    }

    public static List<Vector> loadModel(InputStream file, CosmeticTransformation transformation) throws IOException {

        try (InputStream inputStream = file) {
            Obj obj = ObjReader.read(inputStream);
            Obj normalizedObj = ObjUtils.convertToRenderable(obj);
            List<Vector> vertices = new ArrayList<>();

            // Create a Set to store unique indices of surface vertices
            Set<Integer> surfaceVertexIndices = new HashSet<>();

            // Iterate over all faces and add vertex indices to the Set
            for (int i = 0; i < normalizedObj.getNumFaces(); i++) {
                for (int j = 0; j < normalizedObj.getFace(i).getNumVertices(); j++) {
                    surfaceVertexIndices.add(normalizedObj.getFace(i).getVertexIndex(j));
                }
            }

            Vector scale = transformation.getScale();
            Vector translate = transformation.getTranslate();
            Vector rotate = transformation.getRotation();

            // Iterate over the unique surface vertex indices and add the corresponding vertices to the List
            for (int index : surfaceVertexIndices) {
                double x = normalizedObj.getVertex(index).getX() * scale.getX() + translate.getX();
                double y = normalizedObj.getVertex(index).getY() * scale.getY() + translate.getY();
                double z = normalizedObj.getVertex(index).getZ() * scale.getZ() + translate.getZ();
                Vector vector = new Vector(x, y, z);
                vertices.add(rotateZ(rotateY(rotateX(vector, rotate.getX()), rotate.getY()), rotate.getZ()));
            }

            return vertices;
        }
    }

    public Vector prepare(Vector vector, Player player, AttachedTo attachedTo) {

        Location location = player.getLocation();

        return switch (attachedTo) {
            case HEAD -> {
                Vector clone = vector.clone();
                clone = clone.subtract(new Vector(0, location.getY() - player.getEyeLocation().getY(), 0));
                yield rotateX(rotateY(vector.clone(), -(location.getYaw() + 180)), -location.getPitch());
            }
            case BODY -> null;
        };

    }

    public static Vector rotateX(Vector vector, double degree) {
        double radians = Math.toRadians(degree);
        return new Vector(vector.getX(), vector.getY() * Math.cos(radians) - vector.getZ() * Math.sin(radians), vector.getY() * Math.sin(radians) + vector.getZ() * Math.cos(radians));
    }

    public static Vector rotateY(Vector vector, double degree) {
        double radians = Math.toRadians(degree);
        return new Vector(vector.getX() * Math.cos(radians) + vector.getZ() * Math.sin(radians), vector.getY(), vector.getX() * -Math.sin(radians) + vector.getZ() * Math.cos(radians));
    }

    public static Vector rotateZ(Vector vector, double degree) {
        double radians = Math.toRadians(degree);
        return new Vector(vector.getX() * Math.cos(radians) - vector.getY() * Math.sin(radians), vector.getX() * Math.sin(radians) + vector.getY() * Math.cos(radians), vector.getZ());
    }
}
