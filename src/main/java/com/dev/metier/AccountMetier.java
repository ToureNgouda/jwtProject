package com.dev.metier;

import com.dev.entities.AppRoles;
import com.dev.entities.AppUser;

public interface AccountMetier {
  public AppUser saveUser(AppUser user);
  public AppRoles saveRole(AppRoles role);
  public void AddRoleToUse(String username, String roleName);
  public AppUser getuser(String username);
}
