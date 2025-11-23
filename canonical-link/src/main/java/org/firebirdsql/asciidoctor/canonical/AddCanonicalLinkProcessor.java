package org.firebirdsql.asciidoctor.canonical;

import org.asciidoctor.ast.Document;
import org.asciidoctor.extension.DocinfoProcessor;
import org.asciidoctor.extension.Location;
import org.asciidoctor.extension.LocationType;

/**
 * Docinfo processor that adds a canonical link to the header of an HTML document.
 * <p>
 * The URL is sourced from the document attribute {@code fb-canonical-html}.
 * </p>
 *
 * @since 1
 */
@Location(LocationType.HEADER)
public class AddCanonicalLinkProcessor extends DocinfoProcessor {

    private static final String CANONICAL_URL_ATTRIBUTE = "fb-canonical-html";

    @Override
    public String process(Document document) {
        if (document.isBasebackend("html") && document.hasAttribute(CANONICAL_URL_ATTRIBUTE)) {
            return "<link rel=\"canonical\" href=\"%s\"/>".formatted(document.getAttribute(CANONICAL_URL_ATTRIBUTE));
        }
        return null;
    }

}
