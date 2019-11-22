<template>

  <div class="app-container">
    <div class="container-mian filter-container">

      <div class="filter-container">

        <el-button type="primary" icon="el-icon-plus" @click="handleAdd">增加</el-button>

        <el-table :data="records" style="width: 100%" stripe>
          <el-table-column prop="id" label="id" />
          <el-table-column prop="name" label="名称" />
          <el-table-column prop="age" label="年龄" />
          <el-table-column prop="phone" label="手机号码" />
          <el-table-column prop="co" label="操作" align="right" width="180">
            <template slot-scope="scope">
              <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
              <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

        <el-pagination
          :page-size="pageSize"
          :total="total"
          :page-sizes="[30, 50, 80, 100]"
          :current-page="currentPage"
          layout="total, sizes, prev, pager, next, jumper"
          class="pagination-container"
          background
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
        />

        <!--弹出层-->
        <el-dialog :visible.sync="dialogFormVisible" title="增加">
          <el-form ref="form" :model="form" :rules="rules">=
            <el-form-item :label-width="formLabelWidth" label="名称" prop="name">
              <el-input v-model.trim="form.name" auto-complete="off" class="form-width" placeholder="请输入名称" />
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="年龄" prop="age">
              <el-input v-model.trim="form.age" auto-complete="off" class="form-width" placeholder="请输入年龄" />
            </el-form-item>
            <el-form-item :label-width="formLabelWidth" label="手机号码" prop="phone">
              <el-input v-model.trim="form.phone" auto-complete="off" class="form-width" placeholder="请输入手机号码" />
            </el-form-item>
          </el-form>
          <div slot="footer" class="footer">
            <el-button @click="handleCancel">取 消</el-button>
            <el-button type="primary" @click="handleSubmit('form')">确 定</el-button>
          </div>
        </el-dialog>
        <!--弹出层 end-->

      </div>

    </div>
  </div>

</template>

<script>
import { getPage, saveOrUpdate, removeById } from '@/api/user'
export default {
  data() {
    return {
      rules: {
        name: [
          { required: true, message: '请输入名称', trigger: 'blur' }
        ],
        age: [
          { required: true, message: '请输入年龄', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入手机号码', trigger: 'blur' }
        ]
      },
      form: {
        name: '',
        age: '',
        phone: ''
      },
      formLabelWidth: '30%',

      records: [],
      total: 0,
      pageSize: 30,
      currentPage: 1,

      dialogFormVisible: false
    }
  },
  created() {
    this.getPage(1, 30)
  },
  methods: {
    getPage(current, size) {
      var query = {}
      query.current = current
      query.size = size
      getPage(query).then(response => {
        this.records = response.records
        this.total = response.total
        this.pageSize = response.size
        this.currentPage = response.current
      })
    },
    handleSubmit(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          saveOrUpdate(this.form).then(response => {
            if (response) {
              this.getPage(1, 30)
              this.dialogFormVisible = false
            }
          })
        }
      })
    },
    handleAdd() {
      this.dialogFormVisible = true
    },
    handleEdit(index, row) {
      this.dialogFormVisible = true
      this.form = this.records[index]
    },
    handleDelete(index, row) {
      this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        var query = { id: this.records[index].id }
        removeById(query).then(response => {
          if (response) {
            this.getPage(1, 30)
          }
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消删除'
        })
      })
    },
    handleCancel() {
      this.dialogFormVisible = false
    },
    handleSizeChange(pageSize) {
      this.getPage(this.currentPage, pageSize)
    },
    handleCurrentChange(currentPage) {
      this.getPage(currentPage, this.pageSize)
    }
  }
}
</script>
<style scoped>
  .filter-container {
    margin-top: 20px;
  }
  .notice {
    color: red;
    font-size: 13px;
    text-align: center;
    margin-bottom: 20px;
  }
  .footer {
    text-align: center;
  }
</style>
