import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '设备序列号',
    align: "center",
    sorter: true,
    dataIndex: 'deviceId'
  },
  {
    title: '图片记录',
    align: "center",
    sorter: true,
    dataIndex: 'imageData'
  },
  {
    title: '上传时间',
    align: "center",
    sorter: true,
    dataIndex: 'datatime'
  },
  {
    title: '当前烟虫数量',
    align: "center",
    sorter: true,
    dataIndex: 'bugNumber'
  },
  {
    title: '人工审核数量',
    align: "center",
    sorter: true,
    dataIndex: 'manualNumber'
  },
  {
    title: '状态',
    align: 'center',
    sorter: true,
    dataIndex: 'status',
  },
];

// 高级查询数据
export const superQuerySchema = {
  deviceId: {title: '设备序列号',order: 0,view: 'text', type: 'string',},
  imageData: {title: '图片记录',order: 1,view: 'text', type: 'string',},
  datatime: {title: '上传时间',order: 2,view: 'datetime', type: 'string',},
  bugNumber: {title: '当前烟虫数量',order: 3,view: 'number', type: 'number',},
};
