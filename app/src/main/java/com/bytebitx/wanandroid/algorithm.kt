package com.bytebitx.wanandroid


/**
 * 合并两个有序链表
 */
class ListNode(var `val`: Int) {
    var next: ListNode? = null
 }

// 递归方式
//fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
//    if (list1 == null) {
//        return list2
//    } else if (list2 == null) {
//        return list1
//    } else if (list1.`val` < list2.`val`) {
//        list1.next = mergeTwoLists(list1.next, list2)
//        return list1
//    } else {
//        list2.next = mergeTwoLists(list1, list2.next)
//        return list2
//    }
//}

// 迭代方式
fun mergeTwoLists(list1: ListNode?, list2: ListNode?): ListNode? {
    var l1 = list1
    var l2 = list2
    val prehead: ListNode = ListNode(-1)
    val pre = prehead
    while (list1 != null && list2 != null) {
        if (list1.`val` <= list2.`val`) {
            pre.next = l1
            l1 = l1?.next
        } else {
            pre.next = l2
            l2 = l2?.next
        }
    }
    if (l1 == null) {
        pre.next = l2
    } else {
        pre.next = l1
    }
    return prehead.next
}



fun main(args: Array<String>) {
}