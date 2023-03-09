
var url = window.location.href;
var signOutButton = document.getElementById("sign-out");
const profile = document.getElementsByClassName("profileNavIcon");
const heart = document.getElementsByClassName("heartNavIcon");
const compass = document.getElementsByClassName("compassNavIcon");


if(url.includes("/user/") && (signOutButton != null && signOutButton.value =='')){
  profile[0].innerHTML = "<i class=\"fa-solid fa-user fa-xl\"></i>";
  profile[1].innerHTML = "<i class=\"fa-solid fa-user fa-xl\"></i>";
} else if(url.includes("/followed-users/")){
  heart[0].innerHTML = "<i class=\"fa-solid fa-heart fa-xl\"></i>";
  heart[1].innerHTML = "<i class=\"fa-solid fa-heart fa-xl\"></i>";
} else if(url.includes("/admin-panel") || url.includes("/top-list/") || url.includes("/user/")){
} else{
  compass[0].innerHTML = "<i class=\"fa-solid fa-compass fa-xl\"></i>";
  compass[1].innerHTML = "<i class=\"fa-solid fa-compass fa-xl\"></i>";
}

