var Cart = React.createClass({

  render: function() {

    var totalItens = 0;
    var totalPrice = 0;

    var cartItens = this.props.cart.map((cartItem) => {

      totalItens += cartItem.quantity;
      totalPrice += cartItem.totalItem;

    });
    return (
        <div>

          <p className="Title">Cart</p>
          <p>Total Item: {totalItens}</p>
          <p>Total: {totalPrice}</p>
          <button onClick={() => this.props.changeAppMode('showCart')}
              className='btn btn-primary'>Show Cart</button>
        </div>
    );
  }

});

var ProductRow = React.createClass({

    getInitialState: function() {
        return {
            quantity: ''
        };
    },
    onQuantityChange: function(e) {
  	    this.setState({quantity: e.target.value});
  	},

    render: function() {

    return (
        <tr>
            <td>{this.props.product.name}</td>
            <td>{this.props.product.description}</td>
            <td>${parseFloat(this.props.product.price).toFixed(2)}</td>
            <td>
                <a onClick={() => this.props.addToCart(this.props.product)}
                  className='btn btn-info m-r-1em'> Add to Cart
                </a>
                <a onClick={() => this.props.removeFromCart(this.props.product)}
                  className='btn btn-danger'> Remove from Cart
                </a>
            </td>
            <input type="hidden" name="productId" id={this.props.product.idProduct} value={this.props.product} />
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
                    addToCart={this.props.addToCart}
                    removeFromCart={this.props.removeFromCart} />
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

      $('.page-header h1').text('Cart Items');

      return (
          <div>
              <a href='#'
                  onClick={() => this.props.changeAppMode('read')}
                  className='btn btn-primary margin-bottom-1em'> Shopping Products
              </a>
          </div>
      );
    }
});

var ShowCartItemsRow = React.createClass({

  render: function() {

      return (
          this.props.product.quantity > 0
          ?
          <tr>
              <td>{this.props.product.item.name}</td>
              <td>{this.props.product.item.description}</td>
              <td>{this.props.product.quantity}</td>
              <td>${parseFloat(this.props.product.item.price * this.props.product.quantity).toFixed(2)}</td>
          </tr>
          :
          <tr></tr>
          );
      }
});

var ShowCartItems = React.createClass({

  render: function(){

    var cartItens = this.props.cart.map((cartItem,idx) => {

      return (
          <ShowCartItemsRow
              key={idx}
              product={cartItem} />
      );

    }.bind(this));

    return (
      !cartItens.length
        ? <div>
            <div className='alert alert-danger'>No item on cart.</div>
            <TopActionsComponent changeAppMode={this.props.changeAppMode} />
          </div>
        :
        <div>
          <TopActionsComponent changeAppMode={this.props.changeAppMode} />
          <table className='table table-bordered table-hover'>
              <thead>
                  <tr>
                      <th>Name</th>
                      <th>Description</th>
                      <th>Qtd.</th>
                      <th>Price</th>
                  </tr>
              </thead>
              <tbody>
              {cartItens}
              </tbody>
          </table>
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
        $('.page-header h1').text('Shopping Products');

        return (

            <div className='overflow-hidden'>
                <Cart cart={this.props.cart} changeAppMode={this.props.changeAppMode}/>

                <ProductsTable
                    products={filteredProducts}
                    addToCart={this.props.addToCart}
                    removeFromCart={this.props.removeFromCart}/>
            </div>
        );
    }
});

var Shopping = React.createClass({

		getInitialState: function() {
		    return {
		        currentMode: 'read',
		        productId: null,
            items: [],
            cart: []
		    };
		},
    addToCart: function(item) {
      var found = false;
      var updatedCart = this.state.cart.map((cartItem) => {

        if (cartItem.item.name == item.name) {

          found = true;
          cartItem.quantity++;
          cartItem.totalItem += cartItem.item.price;

          return cartItem;

        } else {

          return cartItem;
        }

      });

      if (!found) {
        updatedCart.push({item, quantity: 1, totalItem: item.price})
      }

      this.setState({
        cart: updatedCart
      });

    },
    removeFromCart: function(item) {
      var found=false;
      var pos = -1;

      var updatedCart = this.state.cart.map((cartItem,idx) => {

        if (cartItem.item.idProduct == item.idProduct) {

          found = true;

          if(cartItem.quantity > 0){
            cartItem.totalItem -= cartItem.item.price;
            cartItem.quantity--;
          }
          else{
              pos = this.state.cart.indexOf(item);
          }

          return cartItem;

        }

      });

      this.setState({quantity: updatedCart.quantity, totalItem: updatedCart.totalPrice});

      if (found && pos != -1) {
        this.setState({cart: this.state.cart.splice(pos,1)});
      }

    },
		changeAppMode: function(newMode){

		    this.setState({currentMode: newMode});

		},
		render: function() {

			var modeComponent =	<ReadProductsComponent
                                      changeAppMode={this.changeAppMode}
                                      addToCart={this.addToCart}
                                      removeFromCart={this.removeFromCart}
                                      cart={this.state.cart}/>;

			switch(this.state.currentMode){

					case 'read':
						break;

          case 'showCart':
            modeComponent = <ShowCartItems changeAppMode={this.changeAppMode} cart={this.state.cart} />;
            break;

					default:

							break;
			}

			return modeComponent;
		}
});

ReactDOM.render(
    <Shopping />,
    document.getElementById('content')
);
