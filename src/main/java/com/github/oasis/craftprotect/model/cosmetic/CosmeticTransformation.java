package com.github.oasis.craftprotect.model.cosmetic;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.util.Vector;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CosmeticTransformation {

    @Expose
    @SerializedName("scale")
    private Vector scale = new Vector(1, 1, 1);
    @Expose
    @SerializedName("translate")
    private Vector translate = new Vector(0, 0, 0);
    @Expose
    @SerializedName("rotation")
    private Vector rotation = new Vector(0, 0, 0);

}
