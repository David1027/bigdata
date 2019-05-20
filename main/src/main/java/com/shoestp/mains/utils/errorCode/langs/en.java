package com.shoestp.mains.utils.errorCode.langs;

import com.shoestp.mains.utils.errorCode.BaseLangs;
import org.springframework.context.annotation.PropertySource;

@PropertySource(
        value = {"/errorCode/en.yml"},
        encoding = "UTF-8")
public class en  implements BaseLangs {
}
