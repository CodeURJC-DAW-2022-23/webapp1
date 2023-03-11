const searchInputs = document.getElementsByClassName("searchBar");

const cards = document.getElementsByClassName("card");
const usernames = document.getElementsByClassName("header");
const postTitles = document.getElementsByClassName("body");

function filterSuggestions(searchInput){
   searchInput.addEventListener("input", e => {
     const value = e.target.value.toLowerCase();
     for (let i = 0; i < cards.length; i++) {
       const isVisible = usernames[i].textContent.toLowerCase().includes(value) ||
                           postTitles[i].textContent.toLowerCase().includes(value);
       cards[i].classList.toggle("hide", !isVisible);
     }
   })
}

filterSuggestions(searchInputs[0])
filterSuggestions(searchInputs[1])

function hideContainer() {
  //[0]: desktop div [1]: mobile div
  //hide suggestion box
  document.getElementsByClassName("autoCompleteBox")[0].style.visibility = "hidden";
  document.getElementsByClassName("autoCompleteBox")[1].style.visibility = "hidden";

  //add border bottom radius
  document.getElementsByClassName("searchBar")[0].style.borderBottomLeftRadius = "10px";
  document.getElementsByClassName("searchBar")[1].style.borderBottomLeftRadius = "10px";
  document.getElementsByClassName("searchWrapper")[0].style.borderBottomRightRadius = "10px";
  document.getElementsByClassName("searchWrapper")[1].style.borderBottomRightRadius = "10px";
}

function showContainer() {
  //[0]: desktop div [1]: mobile div
  //reveal suggestion box
  document.getElementsByClassName("autoCompleteBox")[0].style.visibility = "visible";
  document.getElementsByClassName("autoCompleteBox")[1].style.visibility = "visible";

  //remove border bottom radius
  document.getElementsByClassName("searchBar")[0].style.borderBottomLeftRadius = "0px";
  document.getElementsByClassName("searchBar")[1].style.borderBottomLeftRadius = "0px";
  document.getElementsByClassName("searchWrapper")[0].style.borderBottomRightRadius = "0px";
  document.getElementsByClassName("searchWrapper")[1].style.borderBottomRightRadius = "0px";
}