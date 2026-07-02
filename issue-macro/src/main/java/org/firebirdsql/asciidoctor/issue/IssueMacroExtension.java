// SPDX-FileCopyrightText: Copyright 2026 Mark Rotteveel
// SPDX-License-Identifier: LicenseRef-IDPL-1.0
package org.firebirdsql.asciidoctor.issue;

import org.asciidoctor.Asciidoctor;
import org.asciidoctor.jruby.extension.spi.ExtensionRegistry;

public class IssueMacroExtension implements ExtensionRegistry {

    @Override
    public void register(Asciidoctor asciidoctor) {
        asciidoctor.javaExtensionRegistry()
                .inlineMacro(IssueMacroProcessor.class);
    }

}
