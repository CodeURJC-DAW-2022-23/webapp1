const loadFile = function (event, id) {
  const image = document.getElementById(id);
  image.src = URL.createObjectURL(event.target.files[0]);
};
