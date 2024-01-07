package org.misarch.catalog.graphql.federation

import com.expediagroup.graphql.generator.federation.execution.FederatedTypePromiseResolver
import graphql.schema.DataFetchingEnvironment
import org.misarch.catalog.graphql.dataloader.CategoryDataLoader
import org.misarch.catalog.graphql.model.Category
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.CompletableFuture

/**
 * Federated resolver for [Category]s.
 */
@Component
class CategoryResolver : FederatedTypePromiseResolver<Category> {
    override val typeName: String
        get() = Category::class.simpleName!!

    override fun resolve(
        environment: DataFetchingEnvironment, representation: Map<String, Any>
    ): CompletableFuture<Category?> {
        val id = representation["id"] as String?
        return if (id == null) {
            CompletableFuture.completedFuture(null)
        } else {
            environment.getDataLoader<UUID, Category>(CategoryDataLoader::class.simpleName!!)
                .load(UUID.fromString(id))
        }
    }
}