package model;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Feature {

@SerializedName("type")
@Expose
private String type;
@SerializedName("properties")
@Expose
private FeatureProperties properties;
@SerializedName("geometry")
@Expose
private Geometry geometry;

public String getType() {
return type;
}

public void setType(String type) {
this.type = type;
}

public FeatureProperties getProperties() {
return properties;
}

public void setProperties(FeatureProperties properties) {
this.properties = properties;
}

public Geometry getGeometry() {
return geometry;
}

public void setGeometry(Geometry geometry) {
this.geometry = geometry;
}

}
