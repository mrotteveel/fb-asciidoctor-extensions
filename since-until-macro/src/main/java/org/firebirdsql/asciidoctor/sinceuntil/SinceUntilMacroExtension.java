package org.firebirdsql.asciidoctor.sinceuntil;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

/**
 * Automatic registration of the since and until macros.
 *
 * @since 1
 */
public class SinceUntilMacroExtension implements ExtensionRegistry {

    @Override
    public void register(Asciidoctor asciidoctor) {
        asciidoctor.javaExtensionRegistry()
                .inlineMacro(SinceMacroProcessor.class)
                .inlineMacro(UntilMacroProcessor.class);
    }

}
