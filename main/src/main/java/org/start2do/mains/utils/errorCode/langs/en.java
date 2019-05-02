package org.start2do.mains.utils.errorCode.langs;

import org.springframework.context.annotation.PropertySource;

@PropertySource(
        value = {"/errorCode/en.yml"},
        encoding = "UTF-8")
public class en  implements BaseLangs{
}
