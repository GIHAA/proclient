package com.full.stack.demo.core.common;

public class ModuleConstants {

    public interface ServerErrorMessages {
        String USER_NOT_FOUND = "api.error.user.not-found";
        String USER_LOGIN_FAILED = "api.error.user.login-failed";
    }

    public interface AppErrorMessages {
        String EMPLOYEE_NOT_FOUND = "api.employee.not.found";
        String PURCHASE_ORDER_NOT_FOUND = "api.purchase.order.not.found";
        String DELIVERY_NOT_FOUND = "api.delivery.not.found";
        String INVOICE_LIST_NOT_FOUND = "api.invoices.list.not.found";
        String INVOICE_LIST_EMPTY = "api.invoices.empty";
        String SUPPLIER_NOT_FOUND = "api.supplier.not.found";
        String FAILED_TO_MAKE_PAYMENT = "api.failed.to.pay";
        String PAYMENT_NOT_FOUND = "api.payment.not.found";
    }
}