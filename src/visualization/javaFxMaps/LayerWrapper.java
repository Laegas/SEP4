package visualization.javaFxMaps;

public class LayerWrapper {

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
    // color for features in this layer
    private Color fromColor;
    private Color toColor;

    public LayerWrapper(double minimumAlpha, String alphaProperty, double alphaDividerProperty, String
            visibilityFormula, String
            displayStringFormat, Color fromColor, Color toColor) {
        this.minimumAlpha = minimumAlpha;
        this.alphaProperty = alphaProperty;
        this.alphaDividerProperty = alphaDividerProperty;
        this.visibilityFormula = visibilityFormula;
        this.displayStringFormat = displayStringFormat;
        this.fromColor = fromColor;
        this.toColor = toColor;
    }

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

    public Color getFromColor() {
        return fromColor;
    }

    public Color getToColor() {
        return toColor;
    }
}
