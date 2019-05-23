package com.shoestp.mains.utils.errorcode.langs;

import com.shoestp.mains.utils.errorcode.BaseLangs;
import org.springframework.context.annotation.PropertySource;

@PropertySource(
    value = {"/errorCode/en.yml"},
    encoding = "UTF-8")
public class en implements BaseLangs {}
