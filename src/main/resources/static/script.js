function getLog(){
	let request = new XMLHttpRequest();
	request.open('GET', '/minecraftLog', true);
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
	request.send()
}
setInterval(()=>{getLog()},1000);

document.getElementById("restart").addEventListener('click', restartServer)
function restartServer() {
	let request = new XMLHttpRequest();
	request.open('POST', '/restart', true)
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

function getStatus(){
	let request = new XMLHttpRequest();
	request.open('GET', '/status', true);
	request.onload = function () {
		let log = this.response.split(/\r?\n/);
		let players = log[2].slice(log[2].indexOf('['), log[2].indexOf(']') + 1);
		let playerNames = [];
		if ( !!players ){
			players = JSON.parse(players.replaceAll("'", '"'));
			players.forEach(
				player => {
					playerNames.push(player.split(' ')[0]);
				}
			)
			playerNames  = playerNames.sort();
		}
		showActivePlayers(playerNames);
	}
	request.send()
}

function showActivePlayers( players ) {
	let playersElement = document.getElementById("players");
	let text;
	if (players.length === 1) {
		text = "Bojovnik " + players[0] + ' sa pokusa prezit.';
	} else if (players.length > 1) {
		text = "O slavu bojuju: ";
		players.forEach( p => {text += p + ', '})
		text = text.slice(0, text.length - 2 );
		text += '.';
	} else {
		text = "Chod sa pripojit nech server nie je opusteny."
	}

	playersElement.textContent = text;
}


setInterval(()=>{getStatus()},2000);
