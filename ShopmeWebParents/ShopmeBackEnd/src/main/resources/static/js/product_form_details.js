function addExtraDetailSection() {

    allDivDetails = $("[id^='divDetail']");
    divDetailCount = allDivDetails.length;

    htmlDetailSection = `
        <div class="form-inline" id="divDetail${divDetailCount}">
            <label class="m-3">Name</label>
            <input type="text" class="form-control w-25" name="detailName" maxlength="255" />
            <label class="m-3">Value</label>
            <input type="text" class="form-control w-25" name="detailValue" maxlength="255" />
        </div>
    `;
    $("#divDetailSection").append(htmlDetailSection);

    previousDivDetailSection = allDivDetails.last();
    previousDivDetailId = previousDivDetailSection.attr("id");


    htmlRemoveDetail = `
                <a class="btn fas fa-times-circle fa-2x "
                href="javascript:removeDetailSection('${previousDivDetailId}')"
                    title="Remove this detail"></a>
            `;

    previousDivDetailSection.append(htmlRemoveDetail);

    $("input[name = 'detailName']").last().focus();

}

function removeDetailSection(index){
    $("#" + index).remove();
}