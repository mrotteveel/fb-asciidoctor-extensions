package org.firebirdsql.asciidoctor.sinceuntil;

import org.asciidoctor.extension.Format;
import org.asciidoctor.extension.FormatType;
import org.asciidoctor.extension.Name;

/**
 * Since (version) inline macro.
 * <p>
 * Converts {@code since:[Jaybird 6]} to {@code [.since]_**Since:** Jaybird 6_}.
 * </p>
 *
 * @since 1
 */
@Name("since")
@Format(FormatType.SHORT)
public class SinceMacroProcessor extends AbstractSinceUntilMacroProcessor {

    public SinceMacroProcessor() {
        super(SinceUntilType.SINCE);
    }

}
