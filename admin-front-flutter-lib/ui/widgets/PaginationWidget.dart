import 'package:flutter/material.dart';

class PaginationWidget extends StatelessWidget {
  final int currentPage;
  final int totalRecords; 
  final int pageSize; 
  final ValueChanged<int> onPageChanged;

  PaginationWidget({
    super.key,
    required this.currentPage,
    required this.totalRecords,
    required this.pageSize,
    required this.onPageChanged,
  }){

    //print("PaginationWidget currentPage: $currentPage, totalRecords: $totalRecords, pageSize: $pageSize");


  }

  
  // 计算属性：总页数
  int get totalPages => (totalRecords / pageSize).ceil();

  // 计算属性：首页页码
  int get firstPage => 1;

  // 计算属性：末页页码
  int get lastPage => totalPages;

  // 计算属性：上一页页码
  int get previousPage => (currentPage - 1).clamp(firstPage, lastPage);

  // 计算属性：下一页页码
  int get nextPage => (currentPage + 1).clamp(firstPage, lastPage);

  // 辅助属性：是否有上一页
  bool get hasPrevious => currentPage > firstPage;

  // 辅助属性：是否有下一页
  bool get hasNext => currentPage < lastPage;

  // 生成可见页码列表（更清晰的实现）
  List<int> _generateVisiblePages() {
    const visiblePageCount = 5;
    // 总页数不足时直接返回全部页码
    if (totalPages <= visiblePageCount) {
      return List.generate(totalPages, (i) => i + 1);
    }

    // 动态计算中间页码
    int startPage = (currentPage - visiblePageCount ~/ 2).clamp(1, totalPages - visiblePageCount + 1);
    int endPage = (startPage + visiblePageCount - 1).clamp(startPage, totalPages);

    return List.generate(
      endPage - startPage + 1, 
      (index) => startPage + index
    );
  }

  @override
  Widget build(BuildContext context) {
    return Padding(
      padding: const EdgeInsets.symmetric(vertical: 16),
      child: Column(
        children: [
          Row(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              // 首页按钮
              IconButton(
                icon: const Icon(Icons.first_page),
                onPressed: hasPrevious ? () => onPageChanged(firstPage) : null,
              ),
              // 上一页按钮
              IconButton(
                icon: const Icon(Icons.chevron_left),
                onPressed: hasPrevious ? () => onPageChanged(previousPage) : null,
              ),
              // 页码按钮
              ..._generateVisiblePages().map((page) => Padding(
                padding: const EdgeInsets.symmetric(horizontal: 2),
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    backgroundColor: page == currentPage 
                        ? Colors.blue 
                        : Colors.grey[200],
                  ),
                  onPressed: () => onPageChanged(page),
                  child: Text(
                    '$page',
                    style: TextStyle(
                      color: page == currentPage 
                          ? Colors.white 
                          : Colors.black,
                    ),
                  ),
                ),
              )),
              // 下一页按钮
              IconButton(
                icon: const Icon(Icons.chevron_right),
                onPressed: hasNext ? () => onPageChanged(nextPage) : null,
              ),
              // 末页按钮
              IconButton(
                icon: const Icon(Icons.last_page),
                onPressed: hasNext ? () => onPageChanged(lastPage) : null,
              ),
            ],
          ),
          const SizedBox(height: 8),
          Text(
            '显示 $totalRecords 条记录 · 当前第 $currentPage 页/共 $totalPages 页',
            style: const TextStyle(color: Colors.grey),
          ),
        ],
      ),
    );
  }
}