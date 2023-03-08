
const searchInput = document.querySelector("[data-search]")

const cards = document.getElementsByClassName("card");
const usernames = document.getElementsByClassName("header");
const postTitles = document.getElementsByClassName("body");

searchInput.addEventListener("input", e => {
  const value = e.target.value.toLowerCase()
  for (let i = 0; i < cards.length; i++) {
    const isVisible = usernames[i].textContent.toLowerCase().includes(value) ||
                        postTitles[i].textContent.toLowerCase().includes(value)

    cards[i].classList.toggle("hide", !isVisible)
  }
})


function hideContainer() {
  document.getElementById("autoCompleteBox").style.visibility = "hidden";
  document.getElementById("searchBar").style.borderBottomLeftRadius = "10px";
  document.getElementById("searchWrapper").style.borderBottomRightRadius = "10px";
}

function showContainer() {
  document.getElementById("autoCompleteBox").style.visibility = "visible";
  document.getElementById("searchBar").style.borderBottomLeftRadius  = "0px";
  document.getElementById("searchWrapper").style.borderBottomRightRadius = "0px";
}