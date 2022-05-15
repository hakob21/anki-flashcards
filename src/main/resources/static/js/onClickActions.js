console.log("localporrrtt: " + port)
var tag = document.getElementsByName("word");
// const max = tag.length;
console.log(tag.length)
for (var i1 = 0; i1 < tag.length; i1++) {
    var item = tag[i1];
    item.addEventListener('click', function (event) {
            var xhr = new XMLHttpRequest();
            xhr.open("POST", "http://localhost:" + port + "/translate", true);
            xhr.setRequestHeader('Content-Type', 'application/json');
            xhr.send(JSON.stringify({
                indexOfWordToTranslate: this.id,
                wordToTranslate: document.getElementById(this.id).textContent
            }));
            console.log("Final array " + document.getElementById(this.id).textContent)

            xhr.onreadystatechange = function () {
                if (xhr.readyState == XMLHttpRequest.DONE) {
                    console.log(xhr.responseText)
                    var toastDiv = document.querySelector('#toast-body')
                    toastDiv.innerHTML = xhr.responseText

                    var toast = new bootstrap.Toast(document.querySelector('#basicToast'))
                    toast.show();
                }
            }
            // console.log("Final array " + document.getElementById(this.id))
        }
    )
}
document.querySelector("#basicToastBtn").onclick = function () {
    new bootstrap.Toast(document.querySelector('#basicToast')).show();
}
