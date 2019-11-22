<template>
  <el-scrollbar wrap-class="scrollbar-wrapper">
    <ul id="treeDemo" class="ztree"/>
  </el-scrollbar>
</template>

<script>
import $ from 'jquery'
export default {
  data() {
    return {
      listLoading: true,
      nodeData: [
        { id: 1, pId: 0, name: '父节点' },
        { id: 11, pId: 1, name: '11' }
      ],
      setting: {
        view: {
          showLine: false
        },
        data: {
          simpleData: {
            enable: true
          }
        },
        callback: {
          onClick: this.zTreeOnClick
        }
      }
    }
  },
  mounted() {
    this.zTree()
  },
  methods: {
    zTree() {
      $.fn.zTree.init($('#treeDemo'), this.setting, this.nodeData)
      this.listLoading = false
    },
    zTreeOnClick(event, treeId, treeNode) {
      var name = treeNode.name
      var projectId = treeNode.id
      this.$router.push({
        path: '/ztree',
        query: {
          projectId: projectId,
          projectName: name
        }
      })
    }
  }
}
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .scrollbar-wrapper {
    background:#f5f7fa;
    .el-menu{
      background: none;
    }
  }
  .ztree{
    padding-top:15px;
  }
</style>
