# Assignment on module 15

### **Assignment on module 15**

**Secure Note-Taking Application (Spring Security Basic Auth)**

# **1. Problem Overview**

Your task is to build a Secure **Note-Taking Application** using Spring Boot and Spring Security. The application will enforce role-based access control (RBAC) via **HTTP Basic Authentication**.

The system must support two user roles: USER and ADMIN. Users will manage their private notes, while Administrators will have oversight and management capabilities across all notes in the system.

# **2. Core Requirements**

# **2.1. Security and Authentication**

- **Authentication Mechanism:** HTTP Basic Authentication using Spring Security.
- **User Management:** In-memory or database-backed user store supporting roles.
- **Roles:**
    - USER: Standard note-taking capabilities.
    - ADMIN: System oversight and management.
- 
    
    ![](https://cdn.ostad.app/public/upload/2026-03-11T13-45-23.730Z-Screenshot_12.png)
    
- **2.3. Endpoints and Access Control**
    
    The following RESTful API endpoints must be implemented, with access restricted based on the user's role.
    
    # **User Endpoints (ROLE_USER Access)**
    
    Users can perform full CRUD operations on notes they own.
    
    ![](https://cdn.ostad.app/public/upload/2026-03-11T13-52-23.205Z-Screenshot_33.png)
    

# **Administrator Endpoints (ROLE_ADMIN Access)**

Administrators have global read and delete access.

![](https://cdn.ostad.app/public/upload/2026-03-11T13-52-51.650Z-Screenshot_34.png)

# **Public Endpoints (Registration)**

These endpoints allow new users to be created with a specified role.

![](https://cdn.ostad.app/public/upload/2026-03-11T13-53-16.064Z-Screenshot_35.png)