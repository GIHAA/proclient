package com.full.stack.demo.core.common;


import com.full.stack.demo.core.model.User;
import com.full.stack.demo.core.payload.dto.UserResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring")
public interface MapStructMapper {

    @Mapping(target = "user.id" , source = "id")
    @Mapping(target = "user.firstName" , source = "firstName")
    @Mapping(target = "user.lastname" , source = "lastname")
    @Mapping(target = "user.email" , source = "email")
    @Mapping(target = "user.dateOfBirth" , source = "dateOfBirth")
    @Mapping(target = "user.userRole" , source = "role")
    UserResponseDto userToUserResponseDto(User user);

//    @Mapping(target = "dob", source = "user.dob")
//    @Mapping(target = "gender", source = "user.gender")
//    EmployeeCreateDto employeeToEmployeeDto(SignupRequest user);
//
//    @Mapping(source = "employeeCreateDto.id", target = "employeeId")
//    @Mapping(source = "employeeCreateDto.gender", target = "gender")
//    @Mapping(source = "employeeCreateDto.dob", target = "dateOfBirth")
//    @Mapping(source = "employeeCreateDto.firstName", target = "user.firstName")
//    @Mapping(source = "employeeCreateDto.lastName", target = "user.lastName")
//    @Mapping(source = "employeeCreateDto.email", target = "user.email")
//    Employee employeeCreateDtoToEmployee(EmployeeCreateDto employeeCreateDto);
//
//    @Mapping(source = "id", target = "userId")
//    @Mapping(source = "employee.employeeId", target = "employeeId")
//    @Mapping(source = "email", target = "email")
//    @Mapping(source = "firstName", target = "firstName")
//    @Mapping(source = "lastName", target = "lastName")
//    @Mapping(source = "employee.gender", target = "gender")
//    @Mapping(source = "employee.dateOfBirth", target = "dateOfBirth")
//    @Mapping(source = "role", target = "userRole")
//    EmployeeResponseDto userToEmployeeResponseDto(User user);
//
//    @Mapping(source = "user.id", target = "userId")
//    @Mapping(source = "employeeId", target = "employeeId")
//    @Mapping(source = "user.email", target = "email")
//    @Mapping(source = "user.firstName", target = "firstName")
//    @Mapping(source = "user.lastName", target = "lastName")
//    @Mapping(source = "gender", target = "gender")
//    @Mapping(source = "dateOfBirth", target = "dateOfBirth")
//    @Mapping(source = "user.role", target = "userRole")
//    EmployeeResponseDto employeeToEmployeeResponseDto(Employee employee);
//
//    Supplier supplierCreateDtoToSupplier(SupplierCreateDto supplierCreateDto);
//
//    @Mapping(source = "supplier.user.id", target = "userId")
//    @Mapping(source = "supplier.id", target = "supplierId")
//    SupplierResponseDto supplierToSupplierResponseDto(Supplier supplier);
//
//    List<SupplierResponseDto> supplierListToSupplierResponseDtoList(List<Supplier> suppliers);
//
//    Staff employeeToStaff(Employee employee);
//
//    PurchaseOrderResponseDto purchaseOrderToPurchaseOrderResponseDto(PurchaseOrder order);
//
//    InvoiceResponseDto invoiceToInvoiceResponseDto(Invoice invoice);
//
//    PaymentResponseDto paymentToPaymentResponseDto(Payment payment);
//
//    List<PaymentResponseDto> paymentListToPaymentResponseDtoList(List<Payment> payment);
//
//    ItemResponseDto itemToItemResponseDto(Item item);
//
//    List<ItemResponseDto> itemListToItemResponseDtoList(List<Item> items);
}