package org.firebirdsql.asciidoctor.canonical;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

/**
 * Automatic registration of the canonical-link processor.
 *
 * @since 1
 */
public class CanonicalLinkExtension implements ExtensionRegistry {

    @Override
    public void register(Asciidoctor asciidoctor) {
        asciidoctor.javaExtensionRegistry()
                .docinfoProcessor(AddCanonicalLinkProcessor.class);
    }

}
