function update(name) {
//    get currently selected mode
    var mode = document.getElementById(`${name}-time-type`).value;
    console.log("mode: " + mode);

    var newBox = document.createElement(`div`);
    newBox.className = "col";
    var dynamic = `<div class="form-row">
                            <div class="col">
                                <select class="custom-select" name="zman-${name}" id="zman-${name}" required>
                                                                            <option disabled selected>Choose a zman</option>
                                                                            <option>Netz</option>
                                                                            <option>Chatzot</option>
                                                                            <option>Mincha Gedola</option>
                                                                            <option>Mincha Ketana</option>
                                                                            <option>Plag HaMincha</option>
                                                                            <option>Shekiyah</option>
                                                                            <option>Tzait Hakochavim</option>
                                                                        </select>
                            </div>
                            <div class="col">
                                <input type="number" class="form-control" name="${name}-time-offset" id="${name}-time-offset" required>
                            </div>
                        </div>
                        `;
    var fixed = `<input type="time" class="form-control" name="${name}-fixed-time" id="${name}-fixed-time" required>`;

    var dynamicBox = document.getElementById(`dynamic-time-box-${name}`);
    var fixedBox = document.getElementById(`fixed-time-box-${name}`);

    if (mode == "fixed") {
//        console.log("Removing dynamic box");
        newBox.id = `fixed-time-box-${name}`;
        newBox.innerHTML = fixed;
        dynamicBox.replaceWith(newBox);

//        if (!didSeeDynamicMessage) {
//            showInfoForDynamicTime();
//        }
//        var parentNodeId = dynamicBox.parentNode.id;
//        dynamicBox.remove();
//        document.getElementById(parentNodeId).inn
    } else if (mode == "dynamic") {
//        console.log("Removing static box");
        newBox.id = `dynamic-time-box-${name}`;
        newBox.innerHTML = dynamic;
        fixedBox.replaceWith(newBox);
//        var parentNodeId = staticBox.parentNode.id;
//        staticBox.remove();
//        document.getElementById(parentNodeId).appendChild(dynamic);
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