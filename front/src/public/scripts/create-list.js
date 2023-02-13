const loadFile = function (event, id) {
  const image = document.getElementById(id);
  image.style.height = '100px';
  image.style.width = '100px';
  image.src = URL.createObjectURL(event.target.files[0]);
};
