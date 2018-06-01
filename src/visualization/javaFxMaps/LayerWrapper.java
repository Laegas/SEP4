package visualization.javaFxMaps;

public class LayerWrapper {

    // used in the CheckComboBox in JavaFxMap
    private String layerName;
    // sets minimum opacity for alpha channel of rgba
    private double minimumAlpha;
    // property on which opacity is based
    private String alphaProperty;
    // property which divides alphaProperty, maximal possible alphaProperty
    private double alphaDividerProperty;
    // formula, containing properties, which is evaluated and used for decision whether feature will be visible
    private String visibilityFormula;
    // format of string that is displayed when hovering over feature, @1 through to @9 represent properties
    private String displayStringFormat;
    // property on which color is based
    // TODO replace by ColorProperty
    private String colorProperty;
    // property which divides colorProperty to get weight of that color in visualization
    private String colorDividerProperty;
    // color for features in this layer
    private Color fromColor;
    private Color toColor;

    public LayerWrapper(String layerName, double minimumAlpha, String alphaProperty, double alphaDividerProperty, String
            visibilityFormula, String displayStringFormat, String colorProperty, String colorDividerProperty, Color
            fromColor) {
        this.layerName = layerName;
        this.minimumAlpha = minimumAlpha;
        this.alphaProperty = alphaProperty;
        this.alphaDividerProperty = alphaDividerProperty;
        this.visibilityFormula = visibilityFormula;
        this.displayStringFormat = displayStringFormat;
        this.colorProperty = colorProperty;
        this.colorDividerProperty = colorDividerProperty;
        this.fromColor = fromColor;
        this.toColor = fromColor;
    }

    public void setToColor(Color toColor) {
        this.toColor = toColor;
    }

    public String getLayerName() { return layerName; }

    public double getMinimumAlpha() {
        return minimumAlpha;
    }

    public String getAlphaProperty() {
        return alphaProperty;
    }

    public double getAlphaDividerProperty() {
        return alphaDividerProperty;
    }

    public String getVisibilityFormula() {
        return visibilityFormula;
    }

    public String getDisplayStringFormat() {
        return displayStringFormat;
    }

    public String getColorProperty() { return colorProperty; }

    public String getColorDividerProperty() { return colorDividerProperty; }

    public Color getFromColor() { return fromColor; }

    public Color getToColor() {
        return toColor;
    }
}
