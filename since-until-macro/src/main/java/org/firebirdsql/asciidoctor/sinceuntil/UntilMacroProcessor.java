// SPDX-FileCopyrightText: Copyright 2025 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0
package org.firebirdsql.asciidoctor.sinceuntil;

import org.asciidoctor.extension.Format;
import org.asciidoctor.extension.FormatType;
import org.asciidoctor.extension.Name;

/**
 * Until (version) inline macro.
 * <p>
 * Converts {@code until:[Jaybird 6]} to {@code [.until]_**Removed in:** Jaybird 6_}.
 * </p>
 *
 * @since 1
 */
@Name("until")
@Format(FormatType.SHORT)
public class UntilMacroProcessor extends AbstractSinceUntilMacroProcessor {

    public UntilMacroProcessor() {
        super(SinceUntilType.UNTIL);
    }

}
