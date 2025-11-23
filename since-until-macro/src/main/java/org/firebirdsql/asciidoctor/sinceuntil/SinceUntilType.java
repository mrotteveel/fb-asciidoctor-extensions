package org.firebirdsql.asciidoctor.sinceuntil;

/**
 * Actual type of the since/until macro processor.
 */
enum SinceUntilType {

    SINCE("Since", "since"),
    UNTIL("Removed in", "until");

    private final String defaultText;
    private final String styleName;

    SinceUntilType(String defaultText, String styleName) {
        this.defaultText = defaultText;
        this.styleName = styleName;
    }

    String defaultText() {
        return defaultText;
    }

    String styleName() {
        return styleName;
    }

}
