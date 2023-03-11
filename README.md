# 🎩 Alist

[![license](https://img.shields.io/github/license/CodeURJC-DAW-2022-23/webapp1.svg)](https://github.com/CodeURJC-DAW-2022-23/webapp1/blob/main/LICENSE)

Universal top list website.

## 👤 Collaborators

| Name                        | Email                             | Github Username                                     |
| --------------------------- | --------------------------------- | --------------------------------------------------- |
| Alejandro Porras Torrecilla | a.porras.2019@alumnos.urjc.es     | [skuzow](https://github.com/skuzow)                 |
| Gledrian Gutierrez Regala   | g.gutierrezr.2020@alumnos.urjc.es | [gutche](https://github.com/gutche)                 |
| Alberto Dekenó Maho         | a.dekeno.2020@alumnos.urjc.es     | [kr4ll](https://github.com//kr4ll)                  |
| Frantzes Elzaurdia          | f.elzaurdia.2020@alumnos.urjc.es  | [franchescoURJC](https://github.com/franchescoURJC) |
| Vicente González Pérez      | v.gonzalez.2020@alumnos.urjc.es   | [Vicente1215](https://github.com/Vicente1215)       |

## 💪 Methodology

Agile methodology using scrum framework with jira.

## 💾 Entities

- User.
- List.
- Topic.
- Comments.

## 🚫 User permissions

- **Guest:** View tops.
- **User:** Create tops, manage profile, follow users.
- **Admin:** Manage tops, files & users.

## 🌄 Images

Here will be some images of the website.

## 📊 Graphs

This app is going to have trends (pie chart), registered users (line chart) graphs.

## ⚙️ Complementary technology

We are going to use an api to detect explicit contents.

## 🤔 Algorithm

Website is going to have a recommendation algorithm for welcome page.

Login page, from this page we are going to be able to log into our personal account

# PHASE 2

## Everyone

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

## Logged user
**Profile page** When the user icon is pressed at the navbar, the profile page shows up. Here we can see information about users such as number of people that he follows or his description. 
![image](https://user-images.githubusercontent.com/102819481/224440979-24a15a4a-0291-4288-bf95-dacb9f15ce7b.png)

**Profile page of other users** When we browse a profile page that isn't ours, if we are logged in we are going to be able to see a button to follow the account.
![image](https://user-images.githubusercontent.com/102819481/224441137-96fff5a2-b656-422e-9b07-778039e4dd25.png)

**CreateList page** This page allow the logged in users to create their own Top-List. By adding photos and descriptions to the list items.
![image](https://user-images.githubusercontent.com/102819481/224472181-5c102e4b-2da7-4889-a035-1131d46791a7.png)

**For me page** Once you have logged in you can decide to only see posts from people you are currently following, by pressing the heart button at the navbar
![image](https://user-images.githubusercontent.com/102819481/224473547-364b9566-7bca-4fc0-9928-216911a6deff.png)

## Admin
**Admin Panel page** From this page the admins are going to be able to manage user activities and study the topics graphics
![image](https://user-images.githubusercontent.com/102819481/224444464-11df3f0f-1800-4e89-ac6d-01faa513a275.png)

## Executions intructions
We assume that the computer have Linux as operative system.
* The first point, docker must be installed in order to be able to use the database.
  ![image](https://user-images.githubusercontent.com/102819481/224472613-0b120d6a-54cf-454f-b36b-d5d030d53dc9.png)
  
* Once you have docker installed it must be linked with mysql database.
![image](https://user-images.githubusercontent.com/102819481/224472739-a2a6e336-d98c-45d5-8904-f66a68222114.png)
![image](https://user-images.githubusercontent.com/102819481/224472762-0ae686d8-c54d-438a-a112-ccd69f57f138.png)

* Assuming that the user is using Visual Studio Code, to run the application in visual you need to install the extensions for Springboot
![image](https://user-images.githubusercontent.com/102819481/224472802-a4e61a4f-9d47-48ef-b97e-994c79e271df.png)
as well as an extension for java plus the jdk-17 and Maven 4.0.0
![image](https://user-images.githubusercontent.com/102819481/224472849-6a13acda-e636-4b0e-8141-ed8fa2202f3f.png)

![image](https://user-images.githubusercontent.com/102819481/224472845-125913fd-3127-4bd3-b1a8-be909435c1d2.png)

* Once this is pulished, u can run the application. However, to create an account u need to run maildev, for the confirmation email.
![image](https://user-images.githubusercontent.com/102819481/224473020-27046e69-0f3f-478b-917b-4170094285d9.png)

* Once this is finished we can fully run the application
![image](https://user-images.githubusercontent.com/102819481/224473185-f9ddc4bf-8715-410d-94bd-93d66f8950f5.png)









