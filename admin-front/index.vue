<template>
   <div>
        <div class="filter-container">
          <el-button type="primary" icon="el-icon-plus" @click="handleAdd">增加</el-button>
        </div>
        
        <el-table :data="tableData" style="width: 100%" stripe>
          <el-table-column prop="siteDatasourceId"  label="id"></el-table-column>
          <el-table-column prop="host" label="主机名"></el-table-column>
          <el-table-column prop="port" label="端口"> </el-table-column>
          <el-table-column prop="db" label="数据库名"> </el-table-column>
          <el-table-column prop="userName" label="用户名"> </el-table-column>
          <el-table-column prop="dbType" label="数据源类型"> </el-table-column>
          <el-table-column prop="remarks" label="备注"> </el-table-column>
          <el-table-column prop="co" label="操作" align="right" width="180">
              <template slot-scope="scope">
                  <el-button size="mini" icon="el-icon-edit" @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                  <el-button size="mini" type="danger" icon="el-icon-delete" @click="handleDelete(scope.$index, scope.row)">删除</el-button>
              </template>
          </el-table-column>
        </el-table>

        <el-pagination background @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="currentPage" :page-sizes="[30, 50, 80, 100]" 
              :page-size="pageSize" layout="total, sizes, prev, pager, next, jumper" :total="total" class="pagination-container">
        </el-pagination>

        <!--弹出层-->
        <el-dialog title="增加" :visible.sync="dialogFormVisible">
          <el-form ref="form" :model="form" :rules="rules">
            <el-form-item label="主机名" :label-width="formLabelWidth" prop="host">
              <el-input v-model="form.host" auto-complete="off" class="form-width"></el-input>
            </el-form-item>
            <el-form-item label="端口" :label-width="formLabelWidth" prop="port">
              <el-input v-model="form.port" auto-complete="off" class="form-width"></el-input>
            </el-form-item>
            <el-form-item label="数据库名" :label-width="formLabelWidth" prop="db">
              <el-input v-model="form.db" auto-complete="off" class="form-width"></el-input>
            </el-form-item>
            <el-form-item label="用户名" :label-width="formLabelWidth" prop="userName">
              <el-input v-model="form.userName" auto-complete="off" class="form-width"></el-input>
            </el-form-item>
            <el-form-item label="密码" :label-width="formLabelWidth" prop="password">
              <el-input v-model="form.password" auto-complete="off" class="form-width" type="password"></el-input>
            </el-form-item>
            <el-form-item label="数据源类型" :label-width="formLabelWidth" prop="dbType">
              <el-select v-model="form.dbType" placeholder="请选择数据源类型" class="form-width">
                <el-option label="mysql" value="mysql"></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="备注" :label-width="formLabelWidth" prop="remarks">
              <el-input type="textarea" v-model="form.remarks" rows="5" class="form-width"></el-input>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="insertOrUpdate('form')">确 定</el-button>
            <el-button @click="handleCancel">取 消</el-button>
          </div>
        </el-dialog>
        <!--弹出层 end-->


   </div>
</template>

<script>
    import { selectPage, insertOrUpdate, deleteById } from '@/api/datasource'
    export default {
      data() {
        return {
          rules: {
            host: [
              { required: true, message: '请输入主机', trigger: 'blur' }
            ],
            port: [
              { required: true, message: '请输入端口', trigger: 'blur' }
            ],
            db: [
              { required: true, message: '请输入数据库名', trigger: 'blur' }
            ],
            userName: [
              { required: true, message: '请输入用户名', trigger: 'blur' }
            ],
            password: [
              { required: true, message: '请输入密码', trigger: 'blur' }
            ],
            dbType: [
              { required: true, message: '请输入数据源类型', trigger: 'blur' }
            ],
            remarks: [
              { required: true, message: '请输入备注', trigger: 'blur' }
            ]
          },
          projectId: this.$route.params.projectId,
          loading: true,
          listQuery: {},
          deleteQuery: {},
          tableData: [],
          dialogFormVisible: false,
          form: {
            projectId: this.projectId,
            siteDatasourceId: '',
            host: '',
            port: '',
            db: '',
            dbType: '',
            userName: '',
            password: '',
            remarks: ''
          },
          currentPage: 1,
          total: 0,
          pageSize: 30,
          formLabelWidth: '30%'
        }
      },
      created() {
        this.getTableData(1, 30)
        setTimeout(() => {
          this.loading = false
        }, 200)
      },
      watch: {
        '$route.params.projectId': function(val, oldVal) {
          if (val) {
            this.projectId = val
            this.getTableData(1, 30)
          }
        }
      },
      methods: {
        getTableData(current, size) {
          this.listLoading = true
          this.listQuery.projectId = this.projectId
          this.listQuery.current = current
          this.listQuery.size = size
          this.selectPage(this.listQuery)
        },
        selectPage(listQuery) {
          if (listQuery.projectId === 'all') {
            listQuery.projectId = ''
          }
          selectPage(listQuery).then(response => {
            this.tableData = response.records
            this.total = response.total
            this.pageSize = response.size
            this.currentPage = response.current
          })
        },
        insertOrUpdate(formName) {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              insertOrUpdate(this.form).then(response => {
                if (response) {
                  this.getTableData(1, 30)
                  this.dialogFormVisible = false
                } else {
                  this.$notify({
                    title: '失败',
                    message: '数据源连接不上，请检查用户名/密码是否正确，或权限是否开通',
                    type: 'error',
                    duration: 5000
                  })
                }
              })
            } else {
              console.log('error submit!!')
              return false
            }
          })
        },
        deleteById(deleteQuery) {
          deleteById(deleteQuery).then(response => {
            if (response) {
              this.getTableData(1, 30)
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
            }
          })
        },
        handleAdd() {
          this.form = {
            projectId: this.projectId,
            siteDatasourceId: '',
            host: '',
            port: '',
            db: '',
            dbType: '',
            userName: '',
            password: '',
            remarks: ''
          }
          this.dialogFormVisible = true
        },
        handleEdit(index, row) {
          this.dialogFormVisible = true
          this.form = this.tableData[index]
        },
        handleDelete(index, row) {
          this.$confirm('此操作将永久删除该记录, 是否继续?', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            this.deleteQuery.siteDatasourceId = this.tableData[index].siteDatasourceId
            this.deleteById(this.deleteQuery)
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
          this.getTableData(this.currentPage, pageSize)
        },
        handleCurrentChange(currentPage) {
          this.getTableData(currentPage, this.pageSize)
        }
      }
    }
</script>
<style scoped>

</style>
