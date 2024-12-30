<template>
  <div class="p-2">
    <!--查询区域-->
    <div class="jeecg-basic-table-form-container">
      <a-form ref="formRef" @keyup.enter.native="searchQuery" :model="queryParam" :label-col="labelCol" :wrapper-col="wrapperCol">
        <a-row :gutter="24">
          <a-col :lg="8">
            <a-form-item name="deviceId">
              <template #label><span title="设备序列号">设备序列号</span></template>
              <a-input placeholder="请输入设备序列号" v-model:value="queryParam.deviceId" allow-clear ></a-input>
            </a-form-item>
          </a-col>
<!--          <a-col :lg="6">-->
<!--            <a-form-item name="imageData">-->
<!--              <template #label><span title="图片记录">图片记录</span></template>-->
<!--              <a-input placeholder="请输入图片记录" v-model:value="queryParam.imageData" allow-clear ></a-input>-->
<!--            </a-form-item>-->
<!--          </a-col>-->
<!--          <template v-if="toggleSearchStatus">-->
<!--            <a-col :lg="6">-->
<!--              <a-form-item name="datatime">-->
<!--                <template #label><span title="上传时间">上传时间</span></template>-->
<!--                <a-date-picker showTime valueFormat="YYYY-MM-DD HH:mm:ss" placeholder="请选择上传时间" v-model:value="queryParam.datatime" allow-clear />-->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--            <a-col :lg="6">-->
<!--              <a-form-item name="bugNumber">-->
<!--                <template #label><span title="当前烟虫数量">当前烟虫</span></template>-->
<!--                <a-input-number placeholder="请输入当前烟虫数量" v-model:value="queryParam.bugNumber"></a-input-number>           -->
<!--              </a-form-item>-->
<!--            </a-col>-->
<!--          </template>-->
          <a-col :xl="6" :lg="7" :md="8" :sm="24">
            <span style="float: left; overflow: hidden" class="table-page-search-submitButtons">
              <a-col :lg="6">
                <a-button type="primary" preIcon="ant-design:search-outlined" @click="searchQuery">查询</a-button>
                <a-button type="primary" preIcon="ant-design:reload-outlined" @click="searchReset" style="margin-left: 8px">重置</a-button>
<!--                <a @click="toggleSearchStatus = !toggleSearchStatus" style="margin-left: 8px">-->
<!--                  {{ toggleSearchStatus ? '收起' : '展开' }}-->
<!--                  <Icon :icon="toggleSearchStatus ? 'ant-design:up-outlined' : 'ant-design:down-outlined'" />-->
<!--                </a>-->
              </a-col>
            </span>
          </a-col>
        </a-row>
      </a-form>
    </div>
    <!--引用表格-->
    <BasicTable @register="registerTable" :rowSelection="rowSelection">
      <!--插槽:table标题-->
      <template #tableTitle>
<!--        <a-button type="primary" v-auth="'toolsrecords:tool_records:add'"  @click="handleAdd" preIcon="ant-design:plus-outlined"> 新增</a-button>-->
<!--        <a-button  type="primary" v-auth="'toolsrecords:tool_records:exportXls'" preIcon="ant-design:export-outlined" @click="onExportXls"> 导出</a-button>-->
<!--        <j-upload-button  type="primary" v-auth="'toolsrecords:tool_records:importExcel'"  preIcon="ant-design:import-outlined" @click="onImportXls">导入</j-upload-button>-->
        <a-dropdown v-if="selectedRowKeys.length > 0">
          <template #overlay>
            <a-menu>
              <a-menu-item key="1" @click="batchHandleDelete">
                <Icon icon="ant-design:delete-outlined"></Icon>
                删除
              </a-menu-item>
            </a-menu>
          </template>
          <a-button v-auth="'toolsrecords:tool_records:deleteBatch'">批量操作
            <Icon icon="mdi:chevron-down"></Icon>
          </a-button>
        </a-dropdown>
        <!-- 高级查询 -->
        <super-query :config="superQueryConfig" @search="handleSuperQuery" />
      </template>

      <!-- 表格数据渲染 -->
      <template #bodyCell="{ column, record }">
        <template v-if="column.dataIndex === 'imageData'">
          <img
            :src="record.imageData"
            alt="图片记录"
            style="max-width: 100px; max-height: 100px; border: 1px solid #ddd; border-radius: 4px; padding: 5px; cursor: pointer;"
            @click="handleImageClick(record.imageData)"
          />
        </template>

        <template v-else-if="column.dataIndex === 'status'">
          <!-- 状态列内容动态颜色渲染 -->
          <span :style="getStatusStyle(record.status)">
            {{ record.status }}
          </span>
        </template>

        <template v-else>
          {{ record[column.dataIndex] }}
        </template>
      </template>

      <!--操作栏-->
      <template #action="{ record }">
        <TableAction :actions="getTableAction(record)" :dropDownActions="getDropDownAction(record)"/>
      </template>
<!--      <template v-slot:bodyCell="{ column, record, index, text }">-->
<!--      </template>-->
    </BasicTable>

    <!-- 图片查看模态框 -->
    <a-modal v-model:visible="imageModalVisible" title="图片查看" :footer="null" width="80%" @cancel="handleImageModalCancel"
      bodyStyle="position: relative; display: flex; justify-content: center; align-items: center; height: 80vh; overflow: hidden;">
      <!-- 图片 -->
      <img :src="selectedImage" alt="预览图片"style="max-width: 100%; max-height: 100%; object-fit: contain;"/>
      <!-- 下载按钮 -->
      <a-button type="primary" @click="downloadImage(selectedImage)"style="position: absolute; bottom: 20px; right: 20px;">
        下载图片
      </a-button>
    </a-modal>

    <!-- 表单区域 -->
    <ToolRecordsModal ref="registerModal" @success="handleSuccess"></ToolRecordsModal>



  </div>
</template>

<script lang="ts" setup>
  import { ref, reactive, onMounted } from 'vue';
  import { BasicTable, useTable, TableAction } from '/@/components/Table';
  import { useListPage } from '/@/hooks/system/useListPage';
  import { columns, superQuerySchema } from './ToolRecords.data';
  import { list, deleteOne, batchDelete, getImportUrl, getExportUrl } from './ToolRecords.api';

  import { downloadFile } from '/@/utils/common/renderUtils';
  import ToolRecordsModal from './components/ToolRecordsModal.vue'
  import { useUserStore } from '/@/store/modules/user';

  import { useRoute } from 'vue-router';
  import { message } from 'ant-design-vue';
  import axios from 'axios';


  const formRef = ref();
  const queryParam = reactive<any>({});
  const toggleSearchStatus = ref<boolean>(false);
  const registerModal = ref();
  const userStore = useUserStore();
  //注册table数据
  const { prefixCls, tableContext, onExportXls, onImportXls } = useListPage({
    tableProps: {
      title: '设备记录',
      api: list,
      columns,
      canResize:false,
      useSearchForm: false,
      actionColumn: {
        width: 120,
        fixed: 'right',
      },
      beforeFetch: async (params) => {
        return Object.assign(params, queryParam);
      },
    },
    exportConfig: {
      name: "设备记录",
      url: getExportUrl,
      params: queryParam,
    },
	  importConfig: {
	    url: getImportUrl,
	    success: handleSuccess
	  },
  });
  const [registerTable, { reload, collapseAll, updateTableDataRecord, findTableDataRecord, getDataSource }, { rowSelection, selectedRowKeys }] = tableContext;
  const labelCol = reactive({
    xs:24,
    sm:4,
    xl:6,
    xxl:4
  });
  const wrapperCol = reactive({
    xs: 24,
    sm: 20,
  });

  // 高级查询配置
  const superQueryConfig = reactive(superQuerySchema);

  // 图片模态框状态
  const imageModalVisible = ref(false);
  const selectedImage = ref<string | null>(null);

  const route = useRoute();
  const deviceId = route.query.deviceId; // 获取 deviceId
  queryParam.deviceId = deviceId; // 设置为查询参数

  /**
   * 处理图片点击
   */
  function handleImageClick(imageUrl: string) {
    selectedImage.value = imageUrl;
    imageModalVisible.value = true;
  }

  /**
   * 关闭图片模态框
   */
  function handleImageModalCancel() {
    imageModalVisible.value = false;
    selectedImage.value = null;
  }

  /**
   * 下载图片
   */
  function downloadImage(imageUrl: string) {
    const link = document.createElement('a');
    link.href = imageUrl;
    link.download = 'downloaded_image.jpg'; // 默认文件名
    link.click();
  }


  /**
   * 高级查询事件
   */
  function handleSuperQuery(params) {
    Object.keys(params).map((k) => {
      queryParam[k] = params[k];
    });
    searchQuery();
  }

  /**
   * 新增事件
   */
  function handleAdd() {
    registerModal.value.disableSubmit = false;
    registerModal.value.add();
  }
  
  /**
   * 编辑事件
   */
  function handleEdit(record: Recordable) {
    registerModal.value.disableSubmit = false;
    registerModal.value.edit(record);
  }
   
  /**
   * 详情
   */
  function handleDetail(record: Recordable) {
    registerModal.value.disableSubmit = true;
    registerModal.value.edit(record);
  }
   
  /**
   * 删除事件
   */
  async function handleDelete(record) {
    await deleteOne({ id: record.id }, handleSuccess);
  }
   
  /**
   * 批量删除事件
   */
  async function batchHandleDelete() {
    await batchDelete({ ids: selectedRowKeys.value }, handleSuccess);
  }
   
  /**
   * 成功回调
   */
  function handleSuccess() {
    (selectedRowKeys.value = []) && reload();
  }

  /**
   * 操作栏
   */
  function getTableAction(record) {
    return [
      {
        label: '编辑',
        onClick: handleEdit.bind(null, record),
        auth: 'toolsrecords:tool_records:edit'
      },
    ];
  }
   
  /**
   * 下拉操作栏
   */
  function getDropDownAction(record) {
    return [
      {
        label: '详情',
        onClick: handleDetail.bind(null, record),
      }, {
        label: '删除',
        popConfirm: {
          title: '是否确认删除',
          confirm: handleDelete.bind(null, record),
          placement: 'topLeft',
        },
        auth: 'toolsrecords:tool_records:delete'
      }
    ]
  }

  /**
   * 查询
   */
  function searchQuery() {
    reload();
  }
  
  /**
   * 重置
   */
  function searchReset() {
    formRef.value.resetFields();
    selectedRowKeys.value = [];
    //刷新数据
    reload();
  }

  // 获取状态样式的方法
  function getStatusStyle(status: string) {
    switch (status) {
      case '未上报':
        return { color: 'red', fontWeight: 'bold' };
      case '未审核':
        return { color: 'orange', fontWeight: 'bold' };
      case '已审核':
        return { color: 'green', fontWeight: 'bold' };
      default:
        return {};
    }
  }


</script>

<style lang="less" scoped>
  .jeecg-basic-table-form-container {
    padding: 0;
    .table-page-search-submitButtons {
      display: block;
      margin-bottom: 24px;
      white-space: nowrap;
    }
    .query-group-cust{
      min-width: 100px !important;
    }
    .query-group-split-cust{
      width: 30px;
      display: inline-block;
      text-align: center
    }
    .ant-form-item:not(.ant-form-item-with-help){
      margin-bottom: 16px;
      height: 32px;
    }
    :deep(.ant-picker),:deep(.ant-input-number){
      width: 100%;
    }
  }
</style>
