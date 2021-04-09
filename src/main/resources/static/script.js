var request = new XMLHttpRequest()
request.open('GET', 'http://161.97.68.151:8080/minecraftLog', true)
request.onload = function () {
	let log = this.response.split(/\r?\n/);

	let elementMinecraftLog = document.getElementById("minecraftLog");

	let lineExist = false;
	log.forEach(line => {
		lineExist = false;
		for (let childrenNode of elementMinecraftLog.childNodes) {
			if (childrenNode.textContent === line.toString()){
				lineExist = true
				break;
			}
		}
		if ( lineExist === false){
			let newParagraph = document.createElement('p');
			newParagraph.textContent = line.toString();
			newParagraph.className = "m-0"
			elementMinecraftLog.prepend(newParagraph);
		}
	})
}

var t=setInterval(()=>{request.open('GET', 'http://161.97.68.151:8080/minecraftLog', true);request.send()},1000);

document.getElementById("restart").addEventListener('click', restartServer)

function restartServer() {
	let request = new XMLHttpRequest();
	request.open('POST', 'http://161.97.68.151:8080/restart', true)
	request.onload = function () {
		if (this.status === 200) {
			document.getElementById('toast-body').textContent='Server bol reštartovaný!'

		} else {
			document.getElementById('toast-body').textContent='Server sa nepodarilo reštartovať!'
		}
		$('.toast').toast('show')
	}
	request.send()
}
