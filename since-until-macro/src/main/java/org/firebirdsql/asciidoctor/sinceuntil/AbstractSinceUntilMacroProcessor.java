package org.firebirdsql.asciidoctor.sinceuntil;

import org.asciidoctor.ast.PhraseNode;
import org.asciidoctor.ast.StructuralNode;
import org.asciidoctor.extension.InlineMacroProcessor;

import java.util.Map;

/**
 * Shared implementation of the since/until macro processors.
 *
 * @since 1
 */
public abstract class AbstractSinceUntilMacroProcessor extends InlineMacroProcessor {

    private final SinceUntilType type;

    AbstractSinceUntilMacroProcessor(SinceUntilType type) {
        this.type = type;
    }

    @Override
    public PhraseNode process(StructuralNode parent, String target, Map<String, Object> attributes) {
        String replacement = "[.%s]_**%s:** %s_".formatted(type.styleName(), type.defaultText(), target);
        return createPhraseNode(parent, "quoted", replacement, Map.of("subs", ":normal"));
    }

}
