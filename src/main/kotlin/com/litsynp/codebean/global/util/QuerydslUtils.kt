package com.litsynp.codebean.global.util

import com.litsynp.codebean.domain.codesnippet.QCodeSnippet
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Path
import com.querydsl.core.types.dsl.Expressions
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort

private fun getSortedColumn(parent: Path<*>, fieldName: String, order: Order): OrderSpecifier<*> {
    return OrderSpecifier(order, Expressions.path(Comparable::class.java, parent, fieldName))
}

private fun sortToOrder(order: Sort.Order): Order {
    return if (order.direction.isAscending) Order.ASC else Order.DESC
}

fun getAllOrderSpecifiers(pageable: Pageable, fieldNames: List<String>): List<OrderSpecifier<*>> {
    return pageable.sort.mapNotNull { order ->
        if (order.property in fieldNames) getSortedColumn(
            QCodeSnippet.codeSnippet,
            order.property,
            sortToOrder(order)
        ) else null
    }
}
