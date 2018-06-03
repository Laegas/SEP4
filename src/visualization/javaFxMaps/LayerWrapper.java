package visualization.javaFxMaps;

public class LayerWrapper {

    // used in the CheckComboBox in JavaFxMap
    private String layerName;
    // sets minimum opacity for alpha channel of rgba
    private double minimumAlpha;
    // property on which opacity is based
    private Property alphaProperty;
    // property which divides alphaProperty, maximal possible alphaProperty
    private double alphaDividerProperty;
    // formula, containing properties, which is evaluated and used for decision whether feature will be visible
    private String visibilityFormula;
    // format of string that is displayed when hovering over feature, @1 through to @9 represent properties
    private String displayStringFormat;
    // property on which color is based
    // TODO replace by ColorProperty
    private Property colorProperty;
    // property which divides colorProperty to get weight of that color in visualization
    private Property colorDividerProperty;
    // color for features in this layer
    private Color fromColor;
    private Color toColor;

    public LayerWrapper(String layerName, double minimumAlpha, Property alphaProperty, double alphaDividerProperty,
                        String visibilityFormula, String displayStringFormat, Property colorProperty, Property
                        colorDividerProperty, Color fromColor) {
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

    String getLayerName() { return layerName; }

    double getMinimumAlpha() {
        return minimumAlpha;
    }

    String getAlphaProperty() {
        return alphaProperty.toString();
    }

    double getAlphaDividerProperty() {
        return alphaDividerProperty;
    }

    String getVisibilityFormula() {
        return visibilityFormula;
    }

    String getDisplayStringFormat() {
        return displayStringFormat;
    }

    String getColorProperty() { return colorProperty.toString(); }

    String getColorDividerProperty() { return colorDividerProperty.toString(); }

    Color getFromColor() { return fromColor; }

    Color getToColor() {
        return toColor;
    }
}
