package com.github.oasis.craftprotect.model.cosmetic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.util.Vector;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CosmeticModel {

    @Expose
    @SerializedName("display name")
    private Component displayName;
    @Expose
    @SerializedName("attached to")
    private AttachedTo attachedTo;

    private List<Vector> list;
    @Expose
    @SerializedName("transformation")
    private CosmeticTransformation transformation = new CosmeticTransformation();

}
