const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
const start = urlParams.get('st');
if (start == "sh") {
    document.getElementById("type").value = "shacharit";
} else if (start == "mi") {
    document.getElementById("type").value = "mincha";
} else if (start == "ar") {
    document.getElementById("type").value = "arvit";
} else if (start == "se") {
    document.getElementById("type").value = "selichot";
} else if (start == "mr") {
    document.getElementById("type").value = "megilareading";
}

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
        }
    } else if (mode == "fixed") {
//        console.log("Removing dynamic box");
        newBox.id = `fixed-time-box-${name}`;
        newBox.innerHTML = fixed;
        if (nmBox) {
            nmBox.replaceWith(newBox);
        } else if (dynamicBox) {
            dynamicBox.replaceWith(newBox);
        }
    } else if (mode == "dynamic") {
//        console.log("Removing static box");
        newBox.id = `dynamic-time-box-${name}`;
        newBox.innerHTML = dynamic;
        if (nmBox) {
            nmBox.replaceWith(newBox);
        } else if (fixedBox) {
            fixedBox.replaceWith(newBox);
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

//function validateData() {
//// make sure a minyan type is selected
//    var minyanType = document.getElementById("type").value;
//    if (minyanType == "") {
//        return false;
//    } else {
//    return true;
//}