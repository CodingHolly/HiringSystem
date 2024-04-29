package com.holly.back_end.service;

import com.holly.back_end.entity.Account;
import com.holly.back_end.entity.CompanyAdmin;

public interface ICompanyAdminService {
    CompanyAdmin login(Account account);
}
