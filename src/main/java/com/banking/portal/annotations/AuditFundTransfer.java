package com.banking.portal.annotations;

import java.lang.annotation.*;

@Target(java.lang.annotation.ElementType.METHOD)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
public @interface AuditFundTransfer {
}
