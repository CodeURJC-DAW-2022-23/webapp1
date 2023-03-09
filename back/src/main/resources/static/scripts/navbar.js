
var url = window.location.href;
var signOutButton = document.getElementById("sign-out");

if(url.includes("/user/") && (signOutButton != null && signOutButton.value =='')){
  document.getElementById("profileNavIcon").innerHTML = "<i class=\"fa-solid fa-user fa-xl\"></i>";
} else if(url.includes("/followed-users/")){
  document.getElementById("heartNavIcon").innerHTML = "<i class=\"fa-solid fa-heart fa-xl\"></i>";
} else if(url.includes("/admin-panel") || url.includes("/top-list/") || url.includes("/user/")){
} else{
  document.getElementById("compassNavIcon").innerHTML = "<i class=\"fa-solid fa-compass fa-xl\"></i>";
}

