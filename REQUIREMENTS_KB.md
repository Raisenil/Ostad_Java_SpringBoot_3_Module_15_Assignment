# Module 15 Requirements Knowledge Base

## 1) Objective
Build a secure Note-Taking REST API using Spring Boot with:
- HTTP Basic Authentication
- Role-Based Access Control (RBAC)
- Public registration endpoints

## 2) Roles
- `ROLE_USER`
- `ROLE_ADMIN`

## 3) Authentication and Authorization
- Use **HTTP Basic Auth**.
- Public endpoints:
  - `POST /api/auth/register/user`
  - `POST /api/auth/register/admin`
- Protected endpoint groups:
  - `/api/notes/**` -> `ROLE_USER`
  - `/api/admin/**` -> `ROLE_ADMIN`

## 4) Data Model: Note
| Field | Type | Constraint | Description |
|---|---|---|---|
| `id` | `Long` | Primary Key, Auto-generated | Unique note ID |
| `title` | `String` | Not Null | Title of the note |
| `content` | `String` | Not Null | Body of the note |
| `ownerUsername` | `String` | Not Null | Username of note creator (ownership check) |

## 5) USER Endpoints (`ROLE_USER`)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/notes` | Create a new note. Owner is authenticated user. |
| `GET` | `/api/notes` | Get all notes owned by authenticated user. |
| `GET` | `/api/notes/{id}` | Get note by ID only if owned by authenticated user. |
| `PUT` | `/api/notes/{id}` | Update note by ID only if owned by authenticated user. |
| `DELETE` | `/api/notes/{id}` | Delete note by ID only if owned by authenticated user. |

## 6) ADMIN Endpoints (`ROLE_ADMIN`)
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/admin/notes` | Get all notes in the system (include ID and owner). |
| `DELETE` | `/api/admin/notes/{id}` | Delete any note by ID regardless of owner. |

## 7) Registration Endpoints (Public)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/api/auth/register/user` | Register new user with USER role |
| `POST` | `/api/auth/register/admin` | Register new user with ADMIN role |

## 8) Behavioral Rules
- Ownership is enforced server-side from authenticated principal.
- Client input must not control ownership.
- USER can access only own notes.
- ADMIN can read and delete all notes.
- `title`, `content`, and `ownerUsername` must be non-null.

