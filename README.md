# 1.查找树

左孩子比父节点小，父节点比右孩子小，即中序遍历可以得到从大到小的有序排列

# 2.二叉查找树

二叉树类似下图

![image](https://github.com/shinerio/TreeStructure/blob/master/images/binarytree.png)

二叉查找树时间复杂度不是严格的o(logN)在插入有序数组的时候会出现"链表"的形式，即所有节点只存在左子树或所有节点只存在右子树，查找复杂度会退化到o(N)。而且频繁的删除操作，由于使用右子树最小值替代根节点，会导致二叉树失衡，左子树越来越壮大。

### 2.1.添加节点

从父节点开始，采用递归方法，与当前节点进行比较，相等则在附加域中添加值，若小于，则添加到左子树，若大于则添加到右子树，若当前二叉树中没有此key值的节点存在，则节点会被添加为叶节点（new一个节点对象），然后递归返回链接到父节点中

> 添加节点不会出现节点链接更改的状况

### 2.2.范围查找

从父节点开始，采用递归方法，对于当前节点，若最大值大于当前节点，则先遍历右子树 ，找到上界，然后判断是否满足最小值条件，若满足，则此节点满足筛选条件，若不满足，则直接返回，无须再遍历左子树（左子树必定比右子树小），若右子树全部满足条件，接着搜索左子树。若最大值小于当前节点，则右子树无须遍历，直接搜索左子树。

### 2.3.查找是否存在节点

从父节点开始，比较大小，若待查找值大则，递归查找右子树，若小则递归查找左子树，若等于，则返回true，或者递归到空节点，则表示不存在节点，返回flase

### 2.4.查找最大最小值

从父节点开始递归查找到右子树最深节点为最大值，左子树最深节点为最小值

### 2.5.删除节点

![image](https://github.com/shinerio/TreeStructure/blob/master/images/removebinarynode.png)

# 3.平衡二叉树

父节点的左子树和右子树高度差不能大于1，空节点的高度为-1，空节点高度为0，如图

![image](https://github.com/shinerio/TreeStructure/blob/master/images/avltree.png)

将失衡点进行调整（进行节点旋转）可以得到完全二叉树。

### 3.1.失衡的四种情况

图示略，参考<a href="https://zh.wikipedia.org/wiki/AVL%E6%A0%91">维基百科</a>

注意旋转只能在递归进行其他操作时自动进行调整，这样可以递归重新建立链接

- 左左情况


进行右旋，将失衡点的左孩子作为新的根节点，断开原根节点的左孩子，将新根节点的右孩子作为原根节点的左孩子，将原根节点作为新根节点的右孩子，重新计算各个节点的深度，递归返回根节点，重新建立节点链接。

- 右右情况

进行左旋，将失衡点的右孩子作为新的根节点，断开原根节点的右孩子，将新根节点的左孩子作为原根节点的右孩子，将原根节点作为新根节点的左孩子，重新计算各个节点的深度，递归返回根节点，重新建立节点链接。

- 左右情况

可以分解为两次旋转操作，先对失衡点的左孩子进行一次右右情况的左旋，此时变成左左情况，在对失衡点进行右旋，即可恢复平衡

- 右左情况

可以分解为两次旋转操作，先对失衡点的右孩子进行一次左左情况的右旋，此时变成右右情况，在对失衡点进行左旋，即可恢复平衡

### 3.2.添加节点

原理通过二叉查找树，每次递归之后需要判断是否失衡，然后进行相应的调整并重新计算节点高度

### 3.3.查找是否存在节点

同二叉查找树

### 3.4.查找最大最小值

同二叉查找树

### 3.5.范围查找

同二叉查找树

### 3.6.删除节点

原理同二叉查找树，每次递归之后需要判断是否失衡，然后进行相应的调整并重新计算节点高度

# 4.堆树

### 4.1.添加节点

添加之后需要根据优先级判断左旋或者右选

### 4.2.删除节点

特别之处在于有两个孩子节点时候，需要先判断左右孩子优先级以进行适当旋转，这样避免了二叉查找树只从左子树或只从右子树删除导致树结构失衡的问题。

<a href='http://www.cnblogs.com/huangxincheng/archive/2012/07/21/2602375.html'>参考博客，但原文有相当多错误，谨慎阅读</a>