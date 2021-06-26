var oLanguageLeoCN = {
    'sProcessing': ' 处理中... ',
    'sLengthMenu': ' 显示  _MENU_ 项结果 ',
    'sZeroRecords': ' 没有匹配结果 ',
    'sInfo': ' 显示第_START_至_END_项结果，共_TOTAL_项 ',
    'sInfoEmpty': ' 显示第0至0项结果，共0项 ',
    'sInfoFiltered': ' (由_MAX_项结果过滤) ',
    'sInfoPostFix': '  ',
    'sSearch': ' 搜索: ',
    'sUrl': ' ',
    'sEmptyTable': ' 表中数据为空 ',
    'sLoadingRecords': ' 载入中... ',
    'sInfoThousands': ' , ',
    'oPaginate': {
        'sFirst': ' 首页 ',
        'sPrevious': ' 上页 ',
        'sNext': ' 下页 ',
        'sLast': ' 末页 '
    },
    'oAria': {
        'sSortAscending': ' :以升序排列此列',
        'sSortDescending': ' :以降序排列此列 '
    }
};
$.fn.datepicker.defaults['language'] = 'zh-CN';
$.fn.DataTable.defaults.oLanguage = oLanguageLeoCN;

function accDiv(arg1, arg2) {
    var t1 = 0, t2 = 0, r1, r2;
    try {
        t1 = arg1.toString().split(".")[1].length
    } catch (e) {
    }
    try {
        t2 = arg2.toString().split(".")[1].length
    } catch (e) {
    }
    with (Math) {
        r1 = Number(arg1.toString().replace(".", ""))
        r2 = Number(arg2.toString().replace(".", ""))
        return (r1 / r2) * pow(10, t2 - t1);
    }
} 
