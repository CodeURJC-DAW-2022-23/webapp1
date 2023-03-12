# 🎩 Alist

[![license](https://img.shields.io/github/license/CodeURJC-DAW-2022-23/webapp1.svg)](https://github.com/CodeURJC-DAW-2022-23/webapp1/blob/main/LICENSE)

## Table of contents

- [Phase 0-1](#phase-0-1)
  - [Collaborators](#collaborators)
  - [Methodology](#methodology)
  - [Entities](#entities)
  - [User permissions](#user-permissions)
  - [Images](#images)
  - [Graphs](#graphs)
  - [Complementary Technology](#complementary-technology)
  - [Algorithm](#algorithm)
- [Phase 2](#phase-2)
  - [Guests](#guests)
  - [Logged user](#logged-user)
  - [Admin](#admin)
  - [Execution intructions](#execution-intructions)
  - [Group members participation](#group-members-participation)

A top list generator of any topic you want.

## Phase 0-1

### 👤 Collaborators

| Name                        | Email                             | Github Username                                     |
| --------------------------- | --------------------------------- | --------------------------------------------------- |
| Alejandro Porras Torrecilla | a.porras.2019@alumnos.urjc.es     | [skuzow](https://github.com/skuzow)                 |
| Gledrian Gutierrez Regala   | g.gutierrezr.2020@alumnos.urjc.es | [gutche](https://github.com/gutche)                 |
| Alberto Dekenó Maho         | a.dekeno.2020@alumnos.urjc.es     | [kr4ll](https://github.com//kr4ll)                  |
| Frantzes Elzaurdia          | f.elzaurdia.2020@alumnos.urjc.es  | [franchescoURJC](https://github.com/franchescoURJC) |
| Vicente González Pérez      | v.gonzalez.2020@alumnos.urjc.es   | [Vicente1215](https://github.com/Vicente1215)       |

### 👷🛠️ Methodology

Agile methodology using scrum framework with jira.
![image](https://user-images.githubusercontent.com/102819481/224474020-507a659a-4caa-48a4-a1c0-3499854ddec6.png)

### 💾 Entities

- User.
- List.
- Topic.
- Comments.

### 🚫 User permissions

- **Guest:** View posts and profiles.
- **User:** Create posts, manage profile, follow users, like posts, comment on posts.
- **Admin:** Manage posts, files & users.

### 🌄 Images

Here will be some images of the website.

### 📊 Graphs

This app has a pie chart that shows the popularity of topics based on the number of posts that use said topics.

### ⚙️ Complementary technology

Email verification for user registration.

### 🤔 Algorithm

Website is going to have a recommendation algorithm for home page.

## Phase 2

### Database Diagram
![image](https://user-images.githubusercontent.com/103210832/224561761-28d37e64-cb77-46b2-bd42-d765949c1570.png)


### Guests

**Login page**, from this page we are going to be able to log into our personal account. Two more options are offered here, to enter the page as a guest
or to create an account.
![image](https://user-images.githubusercontent.com/102819481/224439475-3ade71b3-aa19-481b-bffa-6495d4554f2f.png)

**Register page** In case we don't own an account, we are going to be able to create one using the register page.
![image](https://user-images.githubusercontent.com/102819481/224440024-1f720462-fd40-4717-9bd8-5e808143389b.png)

**Feed page** Once we are through the login process, the feed, the principal page is going to be shown. Here the navbar makes his appearance, as well as the posts sorted by date of creation.
![image](https://user-images.githubusercontent.com/102819481/224440471-b4bb66a5-7b98-41da-b1d1-e65f2f20e006.png)

**Search** From the navbar every user is going to be able to look for different posts and users by selecting an option or typing it
![image](https://user-images.githubusercontent.com/102819481/224472448-022df969-3159-4678-b696-639c25a8ecd4.png)

**Profile interactions** If u press the number of people who's following an account you'll be able to see the list of accounts
![image](https://user-images.githubusercontent.com/102819481/224473321-5ac96ca4-46a0-452b-93cc-8f4481bb3f10.png)

Now selecting one of the user from the list we're going to be redirected to the profile we have just selected.

![image](https://user-images.githubusercontent.com/102819481/224473444-18febf14-81e2-447d-a55e-904c02bfe70b.png)

### Logged user

**Profile page** When the user icon is pressed at the navbar, the profile page shows up. Here we can see information about users such as number of people that he follows or his description.
![image](https://user-images.githubusercontent.com/102819481/224440979-24a15a4a-0291-4288-bf95-dacb9f15ce7b.png)

**Profile page of other users** When we browse a profile page that isn't ours, if we are logged in we are going to be able to see a button to follow the account.
![image](https://user-images.githubusercontent.com/102819481/224441137-96fff5a2-b656-422e-9b07-778039e4dd25.png)

**CreateList page** This page allow the logged in users to create their own Top-List. By adding photos and descriptions to the list items.
![image](https://user-images.githubusercontent.com/102819481/224472181-5c102e4b-2da7-4889-a035-1131d46791a7.png)

**For me page** Once you have logged in you can decide to only see posts from people you are currently following, by pressing the heart button at the navbar
![image](https://user-images.githubusercontent.com/102819481/224473547-364b9566-7bca-4fc0-9928-216911a6deff.png)

### Admin

**Admin Panel page** From this page the admins are going to be able to manage user activities and study the topics graphics
!![image](https://user-images.githubusercontent.com/103210832/224559779-4f022381-a0f0-484c-a4d0-fb1dc00e9540.png)

### Execution intructions

- The first point, docker must be installed in order to be able to use the database.
  ![image](https://user-images.githubusercontent.com/102819481/224472613-0b120d6a-54cf-454f-b36b-d5d030d53dc9.png)
- Once you have docker installed it must be linked with mysql database.
  ![image](https://user-images.githubusercontent.com/102819481/224472739-a2a6e336-d98c-45d5-8904-f66a68222114.png)
  ![image](https://user-images.githubusercontent.com/102819481/224472762-0ae686d8-c54d-438a-a112-ccd69f57f138.png)

- Assuming that the user is using Visual Studio Code, to run the application in visual you need to install the extensions for Springboot
  ![image](https://user-images.githubusercontent.com/102819481/224472802-a4e61a4f-9d47-48ef-b97e-994c79e271df.png)
  as well as an extension for java plus the jdk-17 and Maven 4.0.0
  ![image](https://user-images.githubusercontent.com/102819481/224472849-6a13acda-e636-4b0e-8141-ed8fa2202f3f.png)

![image](https://user-images.githubusercontent.com/102819481/224472845-125913fd-3127-4bd3-b1a8-be909435c1d2.png)

- Once this is pulished, u can run the application. However, to create an account u need to run maildev, for the confirmation email.
  ![image](https://user-images.githubusercontent.com/102819481/224473020-27046e69-0f3f-478b-917b-4170094285d9.png)

- Once this is finished we can fully run the application
  ![image](https://user-images.githubusercontent.com/102819481/224473185-f9ddc4bf-8715-410d-94bd-93d66f8950f5.png)

## Group members participation

### Frantzes

#### Textual description: Login, registration (email verification), security configuration (encryption, CSRF protection, HTTPS), search bar, navbar, admin-panel and general styling changes.

#### The five most important commits:

| Commit number | Description                                                                                                                                      |     |
| ------------- | ------------------------------------------------------------------------------------------------------------------------------------------------ | --- |
| #1            | [Top-list page with comments](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/f7773edd1fde10655ee2dd4e0da0b8cfe10993df)                   |
| #2            | [Search bar with autocomplete](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/5a27166caea0cc168e0d74749bc7034d79dbbc55)                  |
| #3            | [Email verification for register](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/153a71fd57b9e936e132f0b909823ae0109c8f5d)               |
| #4            | [Login and registration](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/e5dc5dd816bb85e14ed3c7d55b111c2196b5363d)                        |
| #5            | [Admin-panel layout with collapse and mustache](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/3c26cd8e522eb616057d4d7344d8ae6c7c6d1d46) |

#### The five most participated files:

| File number | File                                                                                                                                                                                              |
| ----------- | ------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1          | [RegistrationService.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/services/RegistrationService.java)                                         |
| #2          | [AdminPanelController.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/controllers/AdminPanelController.java)                                    |
| #3          | [RegisterController.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/controllers/RegisterController.java)                                        |
| #4          | [WebSecurityConfig.java (and entire net.daw.alist.security package)](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/security/WebSecurityConfig.java) |
| #5          | [searchbar.html](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/resources/templates/searchbar.html)                                                                     |

### Vicente

#### Textual description: Database models, database initialize, admin page

#### The five most important commits:

| Commit number | Description |
| ------------- | ----------- |
| #1            |[Complete Admin Panel](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/bdb2398049682f5ec57e4fd38606f801d3a042b5)   |
| #2            |[Database Initialize](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/690f7af51d87efcb54350a67b1da9e769cf7905a)   |
| #3            |[Pie chart](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/cfeb69884f41bd9c0749549f0d4c33b7f4adf5ef)   |
| #4            |[Follow users](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/cd930a7eabbe41a1c5bc63fe0189b30c3c0f97a9)   |
| #5            |[Add topics](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/0dd06422df1453b52d9f9779499ce9855937670c)   |

#### The five most participated files:

| File number | File |
| ----------- | ---- |
| #1          |  [create-chart.js](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/resources/static/scripts/create-chart.js)   |
| #2          |  [Post.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/models/Post.java)   |
| #3          |  [AdminPanelController.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/controllers/AdminPanelController.java)    |
| #4          |  [DatabaseInitializer.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/services/DatabaseInitializer.java)  |
| #5          |   [User.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/models/User.java)   |

### Alberto

#### Textual description:

#### The five most important commits:

| Commit number | Description | Link |
| ------------- | ----------- | ---- |
| #1            |             |      |
| #2            |             |      |
| #3            |             |      |
| #4            |             |      |
| #5            |             |      |

#### The five most participated files:

| File number | File |
| ----------- | ---- |
| #1          |      |
| #2          |      |
| #3          |      |
| #4          |      |
| #5          |      |

### Alejandro

#### Textual description: Profile & Create List, also some models and database init

#### The five most important commits:

| Commit number | Description                                                                                                          |
| ------------- | -------------------------------------------------------------------------------------------------------------------- |
| #1            | [Profile](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/600ef2fc9bec53ad7b143005427fda1188b84b6b)           |
| #2            | [Create List](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/bcbb82f69344b5100150d6604a987bcebbe93719)       |
| #3            | [Models & Database](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/0513473d9193d46b3b00d97dc6124dfed24df4a6) |
| #4            | [Spring init](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/62468402ad4b500a4d73df7e85df02a0d4539ef8)       |
| #5            | [Docker](https://github.com/CodeURJC-DAW-2022-23/webapp1/commit/ca1ec94b0cc25cf9cbd56c267716b9efd4ce64a1)            |

#### The five most participated files:

| File number | File                                                                                                                                                           |
| ----------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| #1          | [ProfileController.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/controllers/ProfileController.java)       |
| #2          | [profile.html](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/resources/templates/profile.html)                                      |
| #3          | [CreateListController.java](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/java/net/daw/alist/controllers/CreateListController.java) |
| #4          | [create-list.html](https://github.com/CodeURJC-DAW-2022-23/webapp1/blame/main/back/src/main/resources/templates/create-list.html)                              |
| #5          | [/models](https://github.com/CodeURJC-DAW-2022-23/webapp1/tree/main/back/src/main/java/net/daw/alist/models)                                                   |

### Gledrian

#### Implemented Home page and For me page that required lazy loading and very specific SQL queries.

#### Five most important commits:

| Commit number | Description         |
| ------------- | ------------------- |
| #1            | Lazy loading script |
| #3            | Home page           |
| #4            | For me page         |
| #5            | SQL queries         |

#### Five most participated files:

| File number | File                |
| ----------- | ------------------- |
| #1          | load-data.js        |
| #2          | PostController.java |
| #3          | PostRepository.java |
| #4          | navbar.html         |
| #5          | /posts models       |
