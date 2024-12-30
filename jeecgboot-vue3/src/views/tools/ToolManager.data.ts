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
    title: '当前烟虫数量',
    align: "center",
    sorter: true,
    dataIndex: 'bugNumber'
  },
  {
    title: '设备位置',
    align: "center",
    sorter: true,
    dataIndex: 'devicePosition'
  },
  {
    title: '设备电量',
    align: "center",
    sorter: true,
    dataIndex: 'deviceElectricity'
  },
  {
    title: '设备信号',
    align: "center",
    sorter: true,
    dataIndex: 'deviceSignal'
  },
  {
    title: '剩余换纸',
    align: "center",
    sorter: true,
    dataIndex: 'repaper'
  },
  {
    title: '剩余天数',
    align: "center",
    sorter: true,
    dataIndex: 'redate'
  },
];

// 高级查询数据
export const superQuerySchema = {
  deviceId: {title: '设备序列号',order: 0,view: 'text', type: 'string',},
  bugNumber: {title: '当前烟虫数量',order: 1,view: 'number', type: 'number',},
  devicePosition: {title: '设备位置',order: 2,view: 'text', type: 'string',},
  deviceElectricity: {title: '设备电量',order: 3,view: 'number', type: 'number',},
  deviceSignal: {title: '设备信号',order: 4,view: 'text', type: 'string',},
  repaper: {title: '剩余换纸',order: 5,view: 'number', type: 'number',},
  redate: {title: '剩余天数',order: 6,view: 'number', type: 'number',},
};
