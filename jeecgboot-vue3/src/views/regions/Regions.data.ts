import {BasicColumn} from '/@/components/Table';
import {FormSchema} from '/@/components/Table';
import { rules} from '/@/utils/helper/validator';
import { render } from '/@/utils/common/renderUtils';
import { getWeekMonthQuarterYear } from '/@/utils';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '区域编号',
    align: "center",
    sorter: true,
    dataIndex: 'regionId'
  },
  {
    title: '区域名称',
    align: "center",
    sorter: true,
    dataIndex: 'regionName'
  },
  {
    title: '负责人',
    align: "center",
    sorter: true,
    dataIndex: 'charger'
  },
  {
    title: '创建日期',
    align: "center",
    sorter: true,
    dataIndex: 'createTime'
  },
  {
    title: '更新日期',
    align: "center",
    sorter: true,
    dataIndex: 'updateTime'
  },
];

// 高级查询数据
export const superQuerySchema = {
  regionId: {title: '区域编号',order: 0,view: 'text', type: 'string',},
  regionName: {title: '区域名称',order: 1,view: 'text', type: 'string',},
  charger: {title: '负责人',order: 2,view: 'text', type: 'string',},
  createTime: {title: '创建日期',order: 3,view: 'datetime', type: 'string',},
  updateTime: {title: '更新日期',order: 4,view: 'datetime', type: 'string',},
};
