package com.iterbio.dscommerce.projections;

public interface UserDetailProjection {

    String getEmail();
    String getPassword();
    String getAuthority();
    Long getRoleId();
}
