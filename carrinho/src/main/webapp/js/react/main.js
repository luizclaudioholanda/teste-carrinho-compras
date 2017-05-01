var CreateProductComponent = React.createClass({

	getInitialState: function() {
	    return {
	        name: '',
	        description: '',
	        price: '',
	        successCreation: null
	    };
	},
	componentDidMount: function (){
		$('.page-header h1').text('Create Product');
	},
	onNameChange: function(e) {
	    this.setState({name: e.target.value});
	},
	onDescriptionChange: function(e) {
	    this.setState({description: e.target.value});
	},
	onPriceChange: function(e) {
	    this.setState({price: e.target.value});
	},
	onSave: function(e){

			const config = { headers: { 'Content-Type': 'application/json' } };

			var data = {
				name: this.state.name,
				description: this.state.description,
				price: this.state.price
			}

			$.ajax({
			    url: 'http://localhost:8000/product/saveProduct',
			    type: 'POST',
			    data: JSON.stringify(data),
			    processData: false,
					contentType: "application/json",
			    success: function () {
						this.setState({successCreation: res});
						this.setState({name: ""});
						this.setState({description: ""});
						this.setState({price: ""});
			    },
			    error: function (xhr, ajaxOptions, thrownError) {
			        alert("Error: " + xhr.status + "\n" +
			            "Message: " + xhr.statusText + "\n" +
			            "Response: " + xhr.responseText + "\n" + thrownError);
			    }
			});
	    e.preventDefault();

	},
 	render: function() {

	    return (
	    <div>
	        {
	           this.state.successCreation == "true" ?
	                <div className='alert alert-success'>
	                    Product was saved.
	                </div>
	            : null
	        }

	        {

	            this.state.successCreation == "false" ?
	                <div className='alert alert-danger'>
	                    Unable to save product. Please try again.
	                </div>
	            : null
	        }

	        <a href='#'
	            onClick={() => this.props.changeAppMode('read')}
	            className='btn btn-primary margin-bottom-1em'> Read Products
	        </a>

	        <form onSubmit={this.onSave}>
	            <table className='table table-bordered table-hover'>
	            <tbody>
	                <tr>
	                    <td>Name</td>
	                    <td>
	                        <input
	                        type='text'
	                        className='form-control'
	                        value={this.state.name}
	                        required
	                        onChange={this.onNameChange} />
	                    </td>
	                </tr>

	                <tr>
	                    <td>Description</td>
	                    <td>
	                        <textarea
	                        type='text'
	                        className='form-control'
	                        required
	                        value={this.state.description}
	                        onChange={this.onDescriptionChange}>
	                        </textarea>
	                    </td>
	                </tr>

	                <tr>
	                    <td>Price ($)</td>
	                    <td>
	                        <input
	                        type='number'
	                        step="0.01"
	                        className='form-control'
	                        value={this.state.price}
	                        required
	                        onChange={this.onPriceChange}/>
	                    </td>
	                </tr>
	 	                <tr>
	                    <td></td>
	                    <td>
	                        <button
	                        className='btn btn-primary'
	                        onClick={this.onSave}>Save</button>
	                    </td>
	                </tr>
	                </tbody>
	            </table>
	        </form>
	    </div>
	  );
	}
});

var ProductRow = React.createClass({

    render: function() {

    return (
        <tr>
            <td>{this.props.product.name}</td>
            <td>{this.props.product.description}</td>
            <td>${parseFloat(this.props.product.price).toFixed(2)}</td>
            <td>
                <a href='#'
                    onClick={() => this.props.changeAppMode('readOne', this.props.product.idProduct)}
                    className='btn btn-info m-r-1em'> Read
                </a>
                <a href='#'
                    onClick={() => this.props.changeAppMode('update', this.props.product.idProduct)}
                    className='btn btn-primary m-r-1em'> Edit
                </a>
                <a
                    onClick={() => this.props.changeAppMode('delete', this.props.product.idProduct)}
                    className='btn btn-danger'> Delete
                </a>
            </td>
        </tr>
        );
    }
});

var ProductsTable = React.createClass({
    render: function() {

    var rows = this.props.products
        .map(function(product, i) {
            return (
                <ProductRow
                    key={i}
                    product={product}
                    changeAppMode={this.props.changeAppMode} />
            );
        }.bind(this));

        return(
            !rows.length
                ? <div className='alert alert-danger'>No products found.</div>
                :
                <table className='table table-bordered table-hover'>
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Description</th>
                            <th>Price</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        {rows}
                    </tbody>
                </table>
        );
    }
});

var TopActionsComponent = React.createClass({
    render: function() {
        return (
            <div>
                <a href='#'
                    onClick={() => this.props.changeAppMode('create')}
                    className='btn btn-primary margin-bottom-1em'> Create Product
                </a>
            </div>
        );
    }
});

var ReadProductsComponent = React.createClass({

    getInitialState: function() {
        return {
            products: []
        };
    },

    componentDidMount: function() {

        this.serverRequest = $.get("http://localhost:8000/product/getAllByOrderByNameAsc", function (response) {

            this.setState({
                products: response
            });
        }.bind(this));
    },

    componentWillUnmount: function() {
        this.serverRequest.abort();
    },

    render: function() {

        var filteredProducts = this.state.products;
        $('.page-header h1').text('Read Products');

        return (
            <div className='overflow-hidden'>
                <TopActionsComponent changeAppMode={this.props.changeAppMode} />

                <ProductsTable
                    products={filteredProducts}
                    changeAppMode={this.props.changeAppMode} />
            </div>
        );
    }
});

var ReadOneProductComponent = React.createClass({

    getInitialState: function() {

        return {
            id: 0,
            name: '',
            description: '',
            price: 0
        };
    },

    // on mount, read one product based on given product ID
    componentDidMount: function() {

        var productId = this.props.productId;

        this.serverRequestProd = $.get("http://localhost:8000/product/getProduct/"+productId,
            function (product) {

                var p = product;
                this.setState({id: p.idProduct});
                this.setState({name: p.name});
                this.setState({description: p.description});
                this.setState({price: p.price});
            }.bind(this));

        $('.page-header h1').text('Read Product');
    },

    componentWillUnmount: function() {
        this.serverRequestProd.abort();
    },

    render: function() {

        return (
            <div>
                <a href='#'
                    onClick={() => this.props.changeAppMode('read')}
                    className='btn btn-primary margin-bottom-1em'>
                    Read Products
                </a>

                <form onSubmit={this.onSave}>
                    <table className='table table-bordered table-hover'>
                        <tbody>
                        <tr>
                            <td>Name</td>
                            <td>{this.state.name}</td>
                        </tr>

                        <tr>
                            <td>Description</td>
                            <td>{this.state.description}</td>
                        </tr>

                        <tr>
                            <td>Price ($)</td>
                            <td>${parseFloat(this.state.price).toFixed(2)}</td>
                        </tr>

                        </tbody>
                    </table>
                </form>
            </div>
        );
    }
});

var UpdateProductComponent = React.createClass({

	getInitialState: function() {
    return {
        idProduct: 0,
        name: '',
        description: '',
        price: 0,
        successUpdate: null
    };
	},
	componentDidMount: function() {

    var productId = this.props.productId;

    this.serverRequestProd = $.get("http://localhost:8000/product/getProduct/"+productId,
        function (product) {
            var p = product;
            this.setState({idProduct: p.idProduct});
            this.setState({name: p.name});
            this.setState({description: p.description});
            this.setState({price: p.price});
        }.bind(this));

    $('.page-header h1').text('Update product');
	},
	componentWillUnmount: function() {
	    this.serverRequestProd.abort();
	},
	onNameChange: function(e) {
	    this.setState({name: e.target.value});
	},
	onDescriptionChange: function(e) {
	    this.setState({description: e.target.value});
	},
	onPriceChange: function(e) {
	    this.setState({price: e.target.value});
	},
	onSave: function(e){

			var data = {
				idProduct: this.state.idProduct,
        name: this.state.name,
        description: this.state.description,
        price: this.state.price
			}
			$.ajax({

					url: 'http://localhost:8000/product/updateProduct',
					type: 'POST',
					data: JSON.stringify(data),
					processData: false,
					contentType: "application/json",
					success: function () {
						this.setState({successCreation: "Product updated"});
						this.setState({name: ''});
						this.setState({description: ''});
						this.setState({price: ''});
					},
					error: function (xhr, ajaxOptions, thrownError) {
							alert("Error: " + xhr.status + "\n" +
									"Message: " + xhr.statusText + "\n" +
									"Response: " + xhr.responseText + "\n" + thrownError);
					}
			});
			e.preventDefault();
	},
	render: function() {

	    return (
	        <div>
	            {
	                this.state.successUpdate == "true" ?
	                    <div className='alert alert-success'>
	                        Product was updated.
	                    </div>
	                : null
	            }

	            {
	                this.state.successUpdate == "false" ?
	                    <div className='alert alert-danger'>
	                        Unable to update product. Please try again.
	                    </div>
	                : null
	            }

	            <a href='#'
	                onClick={() => this.props.changeAppMode('read')}
	                className='btn btn-primary margin-bottom-1em'>
	                Read Products
	            </a>

	            <form onSubmit={this.onSave}>
	                <table className='table table-bordered table-hover'>
	                    <tbody>
	                    <tr>
	                        <td>Name</td>
	                        <td>
	                            <input
	                                type='text'
	                                className='form-control'
	                                value={this.state.name}
	                                required
	                                onChange={this.onNameChange} />
	                        </td>
	                    </tr>

	                    <tr>
	                        <td>Description</td>
	                        <td>
	                            <textarea
	                                type='text'
	                                className='form-control'
	                                required
	                                value={this.state.description}
	                                onChange={this.onDescriptionChange}></textarea>
	                        </td>
	                    </tr>

	                    <tr>
	                        <td>Price ($)</td>
	                        <td>
	                            <input
	                                type='number'
	                                step="0.01"
	                                className='form-control'
	                                value={this.state.price}
	                                required
	                                onChange={this.onPriceChange}/>
	                        </td>
	                    </tr>

	                    <tr>
	                        <td></td>
	                        <td>
	                            <button
	                                className='btn btn-primary'
	                                onClick={this.onSave}>Save Changes</button>
	                        </td>
	                    </tr>

	                    </tbody>
	                </table>
	            </form>
	        </div>
	    );
	}

});

var DeleteProductComponent = React.createClass({

    componentDidMount: function() {
        $('.page-header h1').text('Delete Product');
    },

    onDelete: function(e){
        var productId = this.props.productId;

        $.get("http://localhost:8000/product/deleteProduct/"+productId,
            function(res){
                this.props.changeAppMode('read');
            }.bind(this)
        );
    },

    render: function() {

        return (
            <div className='row'>
                <div className='col-md-3'></div>
                <div className='col-md-6'>
                    <div className='panel panel-default'>
                        <div className='panel-body text-align-center'>Are you sure?</div>
                        <div className='panel-footer clearfix'>
                            <div className='text-align-center'>
                                <button onClick={this.onDelete}
                                    className='btn btn-danger m-r-1em'>Yes</button>
                                <button onClick={() => this.props.changeAppMode('read')}
                                    className='btn btn-primary'>No</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div className='col-md-3'></div>
            </div>
        );
    }
});

var MainApp = React.createClass({

		getInitialState: function() {
		    return {
		        currentMode: 'read',
		        productId: null
		    };
		},
		changeAppMode: function(newMode, productId){

		    this.setState({currentMode: newMode});

		    if(productId !== undefined){
		        this.setState({productId: productId});
		    }
		},
		render: function() {

			var modeComponent =	<ReadProductsComponent changeAppMode={this.changeAppMode} />;

			switch(this.state.currentMode){

					case 'read':
							break;
					case 'readOne':
	            modeComponent = <ReadOneProductComponent productId={this.state.productId} changeAppMode={this.changeAppMode}/>;
	            break;
					case 'update':
		          modeComponent = <UpdateProductComponent productId={this.state.productId} changeAppMode={this.changeAppMode}/>;
		          break;
					case 'create':
							modeComponent = <CreateProductComponent changeAppMode={this.changeAppMode}/>;
							break;
					case 'delete':
		          modeComponent = <DeleteProductComponent productId={this.state.productId} changeAppMode={this.changeAppMode}/>;
		          break;

					default:

							break;
			}

			return modeComponent;
		}
});

ReactDOM.render(
    <MainApp />,
    document.getElementById('content')
);
