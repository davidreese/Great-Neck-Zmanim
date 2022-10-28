function update(name) {
//    get currently selected mode
    var mode = document.getElementById(`${name}-time-type`).value;
    console.log("mode: " + mode);

    var newBox = document.createElement(`div`);
    newBox.className = "col";
    var nm = `<input type="text" class="form-control" name="${name}-fixed-time" id="${name}-fixed-time" disabled>`;
    var fixed = `<input type="time" class="form-control" name="${name}-fixed-time" id="${name}-fixed-time" required>`;
    var dynamic = `<div class="form-row">
                            <div class="col">
                                <select class="custom-select" name="${name}-zman" id="${name}-zman" required>
                                                                            <option disabled selected>Choose a zman</option>
                                                                            <option value="netz">Netz</option>
                                                                            <option value="chatzot">Chatzot</option>
                                                                            <option value="mincha_gedola">Mincha Gedola</option>
                                                                            <option value="mincha_ketana">Mincha Ketana</option>
                                                                            <option value="plag_hamincha">Plag HaMincha</option>
                                                                            <option value="shekiya">Shekiya</option>
                                                                            <option value="tzet">Tzet Hakochavim</option>
                                                                        </select>
                            </div>
                            <div class="col">
                                <input type="number" class="form-control" name="${name}-zman-offset" id="${name}-zman-offset" value=0 required>
                            </div>
                        </div>
                        `;

    var nmBox = document.getElementById(`nm-time-box-${name}`);
    var fixedBox = document.getElementById(`fixed-time-box-${name}`);
    var dynamicBox = document.getElementById(`dynamic-time-box-${name}`);

    if (mode == "nm") {
        newBox.id = `nm-time-box-${name}`;
        newBox.innerHTML = nm;
        if (dynamicBox) {
            dynamicBox.replaceWith(newBox);
        } else if (fixedBox) {
            fixedBox.replaceWith(newBox);
        } else if (nmBox) {
            nmBox.replaceWith(newBox);
        }
    } else if (mode == "fixed") {
//        console.log("Removing dynamic box");
        newBox.id = `fixed-time-box-${name}`;
        newBox.innerHTML = fixed;
        if (nmBox) {
            nmBox.replaceWith(newBox);
        } else if (dynamicBox) {
            dynamicBox.replaceWith(newBox);
        } else if (fixedBox) {
            fixedBox.replaceWith(newBox);
        }
    } else if (mode == "dynamic") {
//        console.log("Removing static box");
        newBox.id = `dynamic-time-box-${name}`;
        newBox.innerHTML = dynamic;
        if (nmBox) {
            nmBox.replaceWith(newBox);
        } else if (fixedBox) {
            fixedBox.replaceWith(newBox);
        } else if (dynamicBox) {
            dynamicBox.replaceWith(newBox);
        }
    } else {
        console.log("Update failed. Mode: " + mode);
        return;
    }
}

function updateAll() {
    update("sunday");
    update("monday");
    update("tuesday");
    update("wednesday");
    update("thursday");
    update("friday");
    update("shabbat");
    update("yt")
    update("rc")
    update("chanuka")
    update("rcc")
}

//updateAll();

function applyMondayThroughFriday() {
    var mondayMode = document.getElementById(`monday-time-type`).value;

    const needToMatch =  ["tuesday", "wednesday", "thursday", "friday"];

    needToMatch.forEach(setMatching);

    function setMatching(name) {
        updateMode(name, mondayMode);
        if (mondayMode == "nm") {

        } else if (mondayMode == "dynamic") {
            var mondayZman = document.getElementById(`monday-zman`).value;
            var mondayOffset = document.getElementById(`monday-zman-offset`).value;
            updateDynamicTime(name, mondayZman, mondayOffset);
        } else if (mondayMode == "fixed") {
            var mondayTime = document.getElementById(`monday-fixed-time`).value;
            updateFixedTime(name, mondayTime);
        }
    }

}

function updateMode(name, mode) {
    document.getElementById(`${name}-time-type`).value = mode;
    update(name);
}

function updateDynamicTime(name, zman, offset) {
    document.getElementById(`${name}-zman`).value = zman;
    document.getElementById(`${name}-zman-offset`).value = offset;
}

function updateFixedTime(name, time) {
    document.getElementById(`${name}-fixed-time`).value = time;
}